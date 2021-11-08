package com.example.cleanweatherapp.di.components

import com.example.cleanweatherapp.di.modules.*
import com.example.cleanweatherapp.ui.MainActivity
import com.example.domain.qualifiers.ApplicationScope
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        NetworkModule::class,
        MapperModule::class,
        RepositoriesModule::class,
        ViewModelModule::class,
        UseCasesModule::class
    ]
)
interface ApplicationComponent {

    fun injectMainActivity(activity: MainActivity)
}