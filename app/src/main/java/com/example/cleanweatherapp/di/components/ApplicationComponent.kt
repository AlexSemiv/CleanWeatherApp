package com.example.cleanweatherapp.di.components

import android.app.Application
import com.example.cleanweatherapp.di.modules.*
import com.example.cleanweatherapp.ui.MainActivity
import com.example.domain.qualifiers.ApplicationScope
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        NetworkModule::class,
        LocalModule::class,
        LocationModule::class
    ]
)
interface ApplicationComponent {

    fun injectActivity(activity: MainActivity)
    fun getMainActivitySubComponent(): MainActivitySubComponent

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun setApplication(application: Application): Builder
        fun build(): ApplicationComponent
    }
}