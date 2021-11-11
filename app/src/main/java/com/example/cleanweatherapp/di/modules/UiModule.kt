package com.example.cleanweatherapp.di.modules

import androidx.recyclerview.widget.DiffUtil
import com.example.cleanweatherapp.ui.main.DailyItemDiffUtil
import com.example.presentation.models.current.Daily
import dagger.Binds
import dagger.Module

@Module
abstract class UiModule {

    @Binds
    abstract fun bindsDailyDiffUtils(
        callback: DailyItemDiffUtil
    ) : DiffUtil.ItemCallback<Daily>
}