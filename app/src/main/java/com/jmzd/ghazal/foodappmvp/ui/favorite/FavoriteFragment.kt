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
        binding = FragmentFavoriteBinding.inflate(layoutInflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //load data rom database
        presenter.loadAllFoods()
    }

    override fun showAllFoods(list: MutableList<FoodEntity>) {

            binding.favoriteList.visibility = View.VISIBLE
            binding.emptyLay.visibility = View.GONE

            favoriteAdapter.setData(list)

            binding.favoriteList.layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.VERTICAL , false)
                binding.favoriteList.adapter = favoriteAdapter


            favoriteAdapter.setOnItemClickListener {
                val direction = FavoriteFragmentDirections.actionToDetailFragment(it.id)
                findNavController().navigate(direction)
            }


    }

    override fun foodsEmpty() {

        binding.apply {
            favoriteList.visibility = View.GONE
            emptyLay.visibility = View.VISIBLE
        }
    }

    override fun onStop() {

        super.onStop()
        presenter.onStop()
    }

}