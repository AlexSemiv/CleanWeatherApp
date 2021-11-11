package com.example.cleanweatherapp.ui.main

import androidx.recyclerview.widget.DiffUtil
import com.example.domain.qualifiers.ActivityScope
import com.example.presentation.models.current.Daily
import javax.inject.Inject

@ActivityScope
class DailyItemDiffUtil @Inject constructor() : DiffUtil.ItemCallback<Daily>() {
    override fun areItemsTheSame(oldItem: Daily, newItem: Daily): Boolean {
        return oldItem.dt == newItem.dt
    }

    override fun areContentsTheSame(oldItem: Daily, newItem: Daily): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}