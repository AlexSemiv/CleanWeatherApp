package com.example.cleanweatherapp.ui.main

import com.example.cleanweatherapp.databinding.MainForecastItemBinding
import com.example.common.base.BaseViewHolder
import com.example.common.other.Constants
import com.example.common.other.Constants.toFormattedDate
import com.example.common.other.Constants.toFormattedDescription
import com.example.presentation.models.current.Daily

class DailyViewHolder(
    val binding: MainForecastItemBinding
) : BaseViewHolder<Daily, MainForecastItemBinding> (binding) {

    override fun bind() {
        item?.let { day ->
            with(binding) {
                (day.dt?.toFormattedDate()).also { tvDate.text = it }
                (day.weather?.description?.toFormattedDescription()).also { tvDescription.text = it }
                (day.temp?.toInt().toString() + Constants.UNITS_OF_MEASUREMENT_TEMP).also { tvTemp.text = it }
                (day.pressure.toString() + "mBar").also { tvPressure.text = it }
                (day.humidity.toString() + "%").also { tvHumidity.text = it }
                (day.wind_speed.toString() + Constants.UNITS_OF_MEASUREMENT_WIND_SPEED).also { tvWindSpeed.text = it }
            }
        }
    }
}