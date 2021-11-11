package com.example.cleanweatherapp.ui.main

import com.example.cleanweatherapp.databinding.MainForecastItemBinding
import com.example.common.base.BaseViewHolder
import com.example.presentation.models.current.Daily

class DailyViewHolder(
    val binding: MainForecastItemBinding
) : BaseViewHolder<Daily, MainForecastItemBinding> (binding) {

    override fun bind() {
        item?.let {
            binding.daily = it
            binding.executePendingBindings()
        }
    }
}