package com.jmzd.ghazal.foodappmvp.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jmzd.ghazal.foodappmvp.R
import com.jmzd.ghazal.foodappmvp.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {

    //Binding
    private lateinit var binding: FragmentFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

}