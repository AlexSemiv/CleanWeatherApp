package com.example.cleanweatherapp.di.modules

import android.app.Application
import com.example.domain.qualifiers.ApplicationScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides

@Module
class LocationModule {

    @ApplicationScope
    @Provides
    fun provideFusedLocationProviderClient(
        application: Application
    ): FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(application)
}