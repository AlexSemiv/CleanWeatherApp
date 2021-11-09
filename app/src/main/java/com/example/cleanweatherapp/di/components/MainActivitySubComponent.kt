package com.example.cleanweatherapp.di.components

import com.example.cleanweatherapp.di.modules.MapperModule
import com.example.cleanweatherapp.di.modules.RepositoriesModule
import com.example.cleanweatherapp.di.modules.UseCasesModule
import com.example.cleanweatherapp.di.modules.ViewModelModule
import com.example.cleanweatherapp.ui.main.MainForecastFragment
import com.example.domain.qualifiers.ActivityScope
import dagger.Subcomponent

@ActivityScope
@Subcomponent(
    modules = [
        ViewModelModule::class,
        MapperModule::class,
        RepositoriesModule::class,
        UseCasesModule::class
    ]
)
interface MainActivitySubComponent {

    fun injectCurrentForecastFragment(fragment: MainForecastFragment)
}