package com.example.cleanweatherapp.di.modules

import android.app.Application
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkRequest
import com.example.domain.qualifiers.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class ConnectionModule {

    @ApplicationScope
    @Provides
    fun provideConnectivityManager(
        application: Application
    ) : ConnectivityManager = application.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

    @ApplicationScope
    @Provides
    fun provideNetworkRequest() : NetworkRequest = NetworkRequest.Builder()
        .addCapability(NET_CAPABILITY_INTERNET)
        .build()
}