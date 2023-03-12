package com.jmzd.ghazal.foodappmvp.ui.favorite

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmzd.ghazal.foodappmvp.data.database.FoodEntity
import com.jmzd.ghazal.foodappmvp.databinding.FragmentFavoriteBinding
import com.jmzd.ghazal.foodappmvp.utils.TAG
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : Fragment() , FavoriteContrracts.View {

    //Binding
    private lateinit var binding: FragmentFavoriteBinding

    @Inject
    lateinit var presenter: FavoritePresenter

    @Inject
    lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        Log.d(TAG, "onCreateView: ")
        binding = FragmentFavoriteBinding.inflate(layoutInflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "onViewCreated: ")
        //load data rom database
        presenter.loadAllFoods()
    }

    override fun showAllFoods(list: MutableList<FoodEntity>) {
        binding.apply {
            Log.d(TAG, "showAllFoods: ")
            favoriteList.visibility = View.VISIBLE
            emptyLay.visibility = View.GONE

            favoriteAdapter.setData(list)

            binding.favoriteList.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = favoriteAdapter
            }

            favoriteAdapter.setOnItemClickListener {
                val direction = FavoriteFragmentDirections.actionToDetailFragment(it.id)
                findNavController().navigate(direction)
            }

        }
    }

    override fun foodsEmpty() {
        Log.d(TAG, "foodsEmpty: ")
        binding.apply {
            favoriteList.visibility = View.GONE
            emptyLay.visibility = View.VISIBLE
        }
    }

    override fun onStop() {
        Log.d(TAG, "onStop: ")
        super.onStop()
        presenter.onStop()
    }

}