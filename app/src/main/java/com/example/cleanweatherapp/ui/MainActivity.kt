package com.example.cleanweatherapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cleanweatherapp.BaseApplication
import com.example.cleanweatherapp.R
import com.example.common.base.BaseActivity
import com.example.cleanweatherapp.databinding.ActivityMainBinding
import com.example.cleanweatherapp.di.components.ApplicationComponent
import com.example.cleanweatherapp.di.components.MainActivitySubComponent

class MainActivity : BaseActivity<ActivityMainBinding>() {

    var activitySubComponent: MainActivitySubComponent? = null

    override fun bindLayout(layoutInflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val applicationComponent = (application as BaseApplication).applicationComponent
        activitySubComponent = applicationComponent.getMainActivitySubComponent()
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
}