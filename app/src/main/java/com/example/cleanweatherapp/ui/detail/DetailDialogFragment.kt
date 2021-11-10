package com.example.cleanweatherapp.ui.detail

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.fragment.navArgs
import com.example.common.base.BaseDialogFragment
import com.example.cleanweatherapp.databinding.MainForecastDialogBinding
import com.example.common.other.ConvertFunctions
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DetailDialogFragment: BaseDialogFragment<MainForecastDialogBinding>(){

    private val args: DetailDialogFragmentArgs by navArgs()

    override fun bindLayout(): MainForecastDialogBinding {
        return MainForecastDialogBinding.inflate(LayoutInflater.from(requireContext()))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        val current = args.current
        binding.apply {
            tvDate.text = ConvertFunctions.formattedCurrentDate(current?.dt ?: 0)
            tvVisibility.text = "${current?.visibility}m"
            tvClouds.text = "${current?.clouds}%"
            tvDevPoint.text = "${current?.dew_point}"
            tvTempAndFeelsLike.text = "${current?.temp} (${current?.feels_like})"
            tvWindDeg.text = "${current?.wind_deg}"
            tvWindGust.text = "${current?.wind_gust}"
            tvWindSpeed.text = "${current?.wind_speed}"
            tvUvi.text = "${current?.uvi}"
        }

        return MaterialAlertDialogBuilder(requireContext())
            .setTitle("Detail information")
            .setView(binding.root)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.cancel()
            }
            .create()
    }
}