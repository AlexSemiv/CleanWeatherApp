package com.example.cleanweatherapp

import android.app.Application
import com.example.cleanweatherapp.di.components.ApplicationComponent
import com.example.cleanweatherapp.di.components.DaggerApplicationComponent

class BaseApplication: Application() {

    val applicationComponent: ApplicationComponent = DaggerApplicationComponent.create()
}