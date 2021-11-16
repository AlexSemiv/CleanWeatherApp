package com.example.cleanweatherapp.ui.search

import androidx.recyclerview.widget.DiffUtil
import com.example.domain.qualifiers.ActivityScope
import com.example.presentation.models.search.ThreeHours
import javax.inject.Inject

@ActivityScope
class ThreeHoursDiffUtil @Inject constructor() : DiffUtil.ItemCallback<ThreeHours>() {
    override fun areItemsTheSame(oldItem: ThreeHours, newItem: ThreeHours): Boolean {
        return oldItem.dt == newItem.dt
    }

    override fun areContentsTheSame(oldItem: ThreeHours, newItem: ThreeHours): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}