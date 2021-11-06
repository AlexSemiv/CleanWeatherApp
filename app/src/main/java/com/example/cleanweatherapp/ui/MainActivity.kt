package com.example.cleanweatherapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cleanweatherapp.R
import com.example.cleanweatherapp.base.BaseActivity
import com.example.cleanweatherapp.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun bindLayout(layoutInflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navController = findNavController(R.id.weatherNavHostFragment)

        binding.bottomNavigationView.apply {
            binding.bottomNavigationView.apply {
                setupWithNavController(
                    navController
                )
                setOnItemReselectedListener {
                    /* No operations */
                }
            }
        }
    }

    /**
         * item recycler .xml +
         *
         * layout for click on chart +
         *
         * layout for header to recyclerView +
         *
         * preferences .xml +
         *
         * dialogFragmentLayout for mainFragment with more information +
         *
         * lottie style +
         *
         * ready app with navigation
     */
}