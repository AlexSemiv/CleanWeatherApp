package com.example.cleanweatherapp.ui.detail

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import com.example.common.base.BaseDialogFragment
import com.example.cleanweatherapp.databinding.MainForecastDialogBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DetailDialogFragment: BaseDialogFragment<MainForecastDialogBinding>(){

    override fun bindLayout(): MainForecastDialogBinding {
        return MainForecastDialogBinding.inflate(LayoutInflater.from(requireContext()))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        binding.apply {
            dialogFragmentDate.text = "19.12.2001"
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