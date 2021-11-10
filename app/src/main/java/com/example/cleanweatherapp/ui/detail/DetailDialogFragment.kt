package com.example.cleanweatherapp.ui.detail

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.fragment.navArgs
import com.example.common.base.BaseDialogFragment
import com.example.cleanweatherapp.databinding.MainForecastDialogBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DetailDialogFragment: BaseDialogFragment<MainForecastDialogBinding>(){

    private val args: DetailDialogFragmentArgs by navArgs()

    override fun bindLayout(): MainForecastDialogBinding {
        return MainForecastDialogBinding.inflate(LayoutInflater.from(requireContext()))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        val current = args.current
        binding.current = current

        return MaterialAlertDialogBuilder(requireContext())
            .setTitle("Detail information")
            .setView(binding.root)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.cancel()
            }
            .create()
    }
}