package com.example.cleanweatherapp.di.modules

import androidx.recyclerview.widget.DiffUtil
import com.example.cleanweatherapp.ui.main.DailyItemDiffUtil
import com.example.cleanweatherapp.ui.search.ThreeHoursDiffUtil
import com.example.presentation.models.current.Daily
import com.example.presentation.models.search.ThreeHours
import dagger.Binds
import dagger.Module

@Module
abstract class UiModule {

    @Binds
    abstract fun bindsDailyDiffUtils(
        callback: DailyItemDiffUtil
    ) : DiffUtil.ItemCallback<Daily>

    @Binds
    abstract fun bindsThreeHoursDiffUtils(
        callback: ThreeHoursDiffUtil
    ) : DiffUtil.ItemCallback<ThreeHours>
}