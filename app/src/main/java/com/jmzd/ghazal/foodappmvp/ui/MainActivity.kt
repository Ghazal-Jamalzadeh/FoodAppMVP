package com.jmzd.ghazal.foodappmvp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.NavHost
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.jmzd.ghazal.foodappmvp.R
import com.jmzd.ghazal.foodappmvp.databinding.ActivityMainBinding
import com.jmzd.ghazal.foodappmvp.utils.TAG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //binding
    private lateinit var binding: ActivityMainBinding

    //nav controller
    private lateinit var navHost: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        /*روش قدیمی */
//        navController = findNavController(R.id.navHost)
//        bottomNav.setupWithNavController(navController)


        //nav controller
        /*تعریف nav controller (حتی زمانی که bottom nav نداریم این را باید بنویسیم ) */
        navHost = supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment


        //BottomNav
        /*وصل کردن nav controller به bottom nav */
        /* قبلا nav controller را به صورت مستقیم تعریف میکردیم و استفاده میکردیم. تو این روش میریم از داخل nav host برش میداریم هر جا نیازش داریم */
        binding.bottomNav.setupWithNavController(navHost.navController)
//        binding.bottomNav.setupWithNavController(findNavController(R.id.navHost))


        //Show bottom nav
        navHost.navController.addOnDestinationChangedListener { _, destination, _ ->

            if (destination.id == R.id.detailFragment) {
                binding.bottomNav.visibility = View.GONE
            } else {
                binding.bottomNav.visibility = View.VISIBLE
            }
        }
    }

    override fun onNavigateUp(): Boolean {
        return navHost.navController.navigateUp() || super.onNavigateUp()
    }
}