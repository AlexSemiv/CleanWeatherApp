package com.example.cleanweatherapp.ui

import android.content.SharedPreferences
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
import com.example.common.other.Constants
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding>() {

    @Inject
    lateinit var preferences: SharedPreferences

    fun setOnUnitsChangeListener(listener: (() -> Unit)) {
        onUnitsChangeListener = listener
    }
    private var onUnitsChangeListener: (() -> Unit)? = null

    private val preferencesListener = SharedPreferences.OnSharedPreferenceChangeListener { preference, key ->
        when (key) {
            Constants.KEY_UNITS -> {
                Constants.setUnitsOfMeasurement(preference.getString(key, "metric") ?: "metric")
                onUnitsChangeListener?.invoke()
            }
            else -> Unit
        }
    }

    var activitySubComponent: MainActivitySubComponent? = null

    override fun bindLayout(layoutInflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val applicationComponent = (application as BaseApplication).applicationComponent
        applicationComponent.injectActivity(this)
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

    override fun onStart() {
        preferences.registerOnSharedPreferenceChangeListener(preferencesListener)
        super.onStart()
    }

    override fun onPause() {
        preferences.unregisterOnSharedPreferenceChangeListener(preferencesListener)
        super.onPause()
    }
}