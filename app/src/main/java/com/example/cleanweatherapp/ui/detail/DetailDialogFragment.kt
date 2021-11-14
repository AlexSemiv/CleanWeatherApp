package com.example.cleanweatherapp.ui.detail

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.fragment.navArgs
import com.example.cleanweatherapp.R
import com.example.common.base.BaseDialogFragment
import com.example.cleanweatherapp.databinding.MainForecastDialogBinding
import com.example.common.other.Constants
import com.example.common.other.Constants.toFormattedDate
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DetailDialogFragment: BaseDialogFragment<MainForecastDialogBinding>(){

    private val args: DetailDialogFragmentArgs by navArgs()

    override fun bindLayout(): MainForecastDialogBinding {
        return MainForecastDialogBinding.inflate(LayoutInflater.from(requireContext()))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        with(binding) {
            args.current?.let { cur ->
                (cur.dt?.toFormattedDate()).also { tvDate.text = it}
                ("Temperature: " + cur.temp?.toInt().toString() + Constants.UNITS_OF_MEASUREMENT_TEMP + " (" +
                        cur.feels_like?.toInt().toString() + Constants.UNITS_OF_MEASUREMENT_TEMP + ")").also { tvTempAndFeelsLike.text = it }
                ("Dew point: " + cur.dew_point?.toInt().toString() + Constants.UNITS_OF_MEASUREMENT_TEMP).also { tvDevPoint.text = it }
                ("Uvi: " + cur.uvi?.toString()).also { tvUvi.text = it }
                ("Clouds: " + cur.clouds?.toString() + "%").also { tvClouds.text = it }
                ("Visibility: " + cur.visibility?.toString() + "m").also { tvVisibility.text = it }
                ("Wind speed: " + cur.wind_speed?.toString() + Constants.UNITS_OF_MEASUREMENT_WIND_SPEED).also { tvWindSpeed.text = it }
                ("Wind deg: " + cur.wind_deg?.toString() + "°").also { tvWindDeg.text = it }
                ("Wind gust: " + cur.wind_gust?.toString() + Constants.UNITS_OF_MEASUREMENT_WIND_SPEED).also { tvWindGust.text = it }
            }
        }

        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.title_detail_dialog))
            .setIcon(R.drawable.ic_umbrella)
            .setView(binding.root)
            .setPositiveButton(getString(R.string.positive_detail_dialog)) { dialog, _ ->
                dialog.cancel()
            }
            .create()
    }
}