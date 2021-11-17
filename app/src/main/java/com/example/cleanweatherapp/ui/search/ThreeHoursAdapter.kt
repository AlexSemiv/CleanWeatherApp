package com.example.cleanweatherapp.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.cleanweatherapp.databinding.SearchForecastItemBinding
import com.example.common.base.BaseRecyclerAdapter
import com.example.presentation.models.search.ThreeHours
import javax.inject.Inject

class ThreeHoursAdapter @Inject constructor(
    callback: DiffUtil.ItemCallback<ThreeHours>
) : BaseRecyclerAdapter<ThreeHours, SearchForecastItemBinding, ThreeHoursViewHolder>(callback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThreeHoursViewHolder {
        return ThreeHoursViewHolder(
            binding = SearchForecastItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}