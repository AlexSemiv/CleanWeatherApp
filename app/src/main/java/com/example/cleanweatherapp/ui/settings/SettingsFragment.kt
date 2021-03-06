package com.example.cleanweatherapp.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.cleanweatherapp.R
import com.example.common.base.BaseFragment
import com.example.cleanweatherapp.databinding.SettingsFragmentBinding

class SettingsFragment: BaseFragment<SettingsFragmentBinding>() {

    override fun bindLayout(
        inflater: LayoutInflater,
        viewGroup: ViewGroup?,
        attachToRoot: Boolean
    ): SettingsFragmentBinding {
        return SettingsFragmentBinding.inflate(inflater, viewGroup, attachToRoot)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentFragmentManager.beginTransaction()
            .replace(R.id.settingsPlaceHolder, SettingsPreference())
            .commit()

        binding.toolbar.setNavigationOnClickListener {
            if (findNavController().previousBackStackEntry != null)
                findNavController().popBackStack()
        }
    }
}