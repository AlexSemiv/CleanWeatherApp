package com.example.cleanweatherapp.di.components

import com.example.cleanweatherapp.di.modules.*
import com.example.cleanweatherapp.ui.main.MainForecastFragment
import com.example.cleanweatherapp.ui.search.SearchForecastFragment
import com.example.domain.qualifiers.ActivityScope
import dagger.Subcomponent

@ActivityScope
@Subcomponent(
    modules = [
        ViewModelModule::class,
        MapperModule::class,
        RepositoriesModule::class,
        UseCasesModule::class,
        UiModule::class
    ]
)
interface MainActivitySubComponent {

    fun injectCurrentForecastFragment(fragment: MainForecastFragment)
    fun injectSearchForecastFragment(fragment: SearchForecastFragment)
}