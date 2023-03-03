package com.jmzd.ghazal.foodappmvp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavHost
import androidx.navigation.fragment.NavHostFragment
import com.jmzd.ghazal.foodappmvp.R
import com.jmzd.ghazal.foodappmvp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //binding
    private lateinit var binding: ActivityMainBinding

    //nav controller
    private lateinit var navHost: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //nav controller
        navHost = supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment

    }
}