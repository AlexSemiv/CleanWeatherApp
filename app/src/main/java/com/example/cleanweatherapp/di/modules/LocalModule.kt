package com.example.cleanweatherapp.di.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.example.domain.qualifiers.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class LocalModule {

    @ApplicationScope
    @Provides
    fun provideSharedPreferences(
        application: Application
    ): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(application)
}