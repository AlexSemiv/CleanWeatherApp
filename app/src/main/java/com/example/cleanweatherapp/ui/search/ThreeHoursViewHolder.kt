package com.example.cleanweatherapp.ui.search

import com.example.cleanweatherapp.R
import com.example.cleanweatherapp.databinding.SearchForecastItemBinding
import com.example.common.base.BaseViewHolder
import com.example.common.other.Constants
import com.example.common.other.Constants.toFormattedFullTime
import com.example.common.other.Constants.toFormattedDescription
import com.example.presentation.models.search.ThreeHours
import java.lang.Exception

class ThreeHoursViewHolder(
    val binding: SearchForecastItemBinding
) : BaseViewHolder<ThreeHours, SearchForecastItemBinding>(binding) {
    override fun bind() {
        item?.let { threeHours ->
            with(binding) {
                (threeHours.dt?.toFormattedFullTime()).also { searchItemDate.text = it }
                (threeHours.windSpeed.toString() + Constants.UNITS_OF_MEASUREMENT_WIND_SPEED).also { searchItemWindSpeed.text = it }
                (threeHours.main?.pressure.toString() + "mBar").also { searchItemPressure.text = it }
                (threeHours.main?.humidity.toString() + "%").also { searchItemHumidity.text = it }
                (threeHours.main?.temp?.toInt().toString() + Constants.UNITS_OF_MEASUREMENT_TEMP).also { searchItemTemp.text = it }
                (threeHours.weather?.description?.toFormattedDescription()).also { searchItemDescription.text = it }
                (when(threeHours.weather?.icon) {
                    "01d" -> R.raw.clear_sky_day
                    "01n" -> R.raw.clear_sky_night
                    "02d", "03d", "04d" -> R.raw.cloudy_day
                    "02n", "03n", "04n" -> R.raw.cloudy_night
                    "09d", "10d" -> R.raw.rain_day
                    "09n", "10n" -> R.raw.rain_night
                    "11d", "11n" -> R.raw.thunder
                    "13d" -> R.raw.snow_day
                    "13n" -> R.raw.snow_night
                    "50d" -> R.raw.mist_day
                    "50n" -> R.raw.mist_night
                    else -> R.raw.clear_sky_day
                }).also { try { searchItemIcon.setAnimation(it) } catch (e: Exception) { searchItemIcon.setAnimation(R.raw.clear_sky_day) } }
            }
        }
    }
}