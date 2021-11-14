package com.example.cleanweatherapp.ui.detail

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.fragment.navArgs
import com.example.cleanweatherapp.R
import com.example.cleanweatherapp.databinding.DailyForecastDialogBinding
import com.example.common.base.BaseDialogFragment
import com.example.common.other.Constants
import com.example.common.other.Constants.toFormattedDate
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ItemDialogFragment : BaseDialogFragment<DailyForecastDialogBinding>() {

    private val args: ItemDialogFragmentArgs by navArgs()

    override fun bindLayout(): DailyForecastDialogBinding {
        return DailyForecastDialogBinding.inflate(LayoutInflater.from(requireContext()))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        with(binding) {
            args.daily?.let { day ->
                (day.dt?.toFormattedDate()).also { tvDate.text = it}
                ("Temperature: " + day.temp?.toInt().toString() + Constants.UNITS_OF_MEASUREMENT_TEMP + " (" +
                        day.feels_like?.toInt().toString() + Constants.UNITS_OF_MEASUREMENT_TEMP + ")").also { tvTempAndFeelsLike.text = it }
                ("Dew point: " + day.dew_point?.toInt().toString() + Constants.UNITS_OF_MEASUREMENT_TEMP).also { tvDevPoint.text = it }
                ("Uvi: " + day.uvi?.toString()).also { tvUvi.text = it }
                ("Clouds: " + day.clouds + "%").also { tvClouds.text = it }
                ("Show: " + (day.snow ?: "not")).also { tvSnow.text = it }
                ("Rain: " + (day.rain ?: "not")).also { tvRain.text = it }
                ("Wind speed: " + day.wind_speed?.toString() + Constants.UNITS_OF_MEASUREMENT_WIND_SPEED).also { tvWindSpeed.text = it }
                ("Wind deg: " + day.wind_deg?.toString() + "°").also { tvWindDeg.text = it }
                ("Wind gust: " + day.wind_gust?.toString() + Constants.UNITS_OF_MEASUREMENT_WIND_SPEED).also { tvWindGust.text = it }
            }
        }

        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.title_item_dialog))
            .setIcon(R.drawable.ic_calendar)
            .setView(binding.root)
            .setPositiveButton(getString(R.string.positive_item_dialog)) { dialog, _ ->
                dialog.cancel()
            }
            .create()
    }
}