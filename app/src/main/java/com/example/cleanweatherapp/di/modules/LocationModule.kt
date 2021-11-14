package com.example.cleanweatherapp.di.modules

import android.app.Application
import com.example.common.other.Constants
import com.example.domain.qualifiers.ApplicationScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
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

    @ApplicationScope
    @Provides
    fun provideLocationRequest(): LocationRequest = LocationRequest.create().apply {
        interval = Constants.LOCATION_REQUEST_INTERVAL
        fastestInterval = Constants.FASTEST_LOCATION_INTERVAL
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }
}