package com.example.cleanweatherapp.di.components

import com.example.cleanweatherapp.di.modules.MapperModule
import com.example.cleanweatherapp.di.modules.NetworkModule
import com.example.cleanweatherapp.di.modules.RepositoriesModule
import com.example.domain.qualifiers.ApplicationScope
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        NetworkModule::class,
        MapperModule::class,
        RepositoriesModule::class
    ]
)
interface ApplicationComponent {
}