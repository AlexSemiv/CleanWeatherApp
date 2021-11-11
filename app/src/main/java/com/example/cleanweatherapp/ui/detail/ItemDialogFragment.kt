package com.example.cleanweatherapp.ui.detail

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.fragment.navArgs
import com.example.cleanweatherapp.databinding.DailyForecastDialogBinding
import com.example.common.base.BaseDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ItemDialogFragment : BaseDialogFragment<DailyForecastDialogBinding>() {

    private val args: ItemDialogFragmentArgs by navArgs()

    override fun bindLayout(): DailyForecastDialogBinding {
        return DailyForecastDialogBinding.inflate(LayoutInflater.from(requireContext()))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        val daily = args.daily
        binding.daily = daily

        return MaterialAlertDialogBuilder(requireContext())
            .setTitle("Weather forecast")
            .setView(binding.root)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.cancel()
            }
            .create()
    }
}