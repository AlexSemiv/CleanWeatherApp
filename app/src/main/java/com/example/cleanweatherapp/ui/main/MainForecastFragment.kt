package com.example.cleanweatherapp.ui.main

import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.findNavController
import com.example.cleanweatherapp.R
import com.example.common.base.BaseFragment
import com.example.cleanweatherapp.databinding.MainForecastFragmentBinding

class MainForecastFragment: BaseFragment<MainForecastFragmentBinding>() {

    override fun bindLayout(
        inflater: LayoutInflater,
        viewGroup: ViewGroup?,
        attachToRoot: Boolean
    ): MainForecastFragmentBinding {
        return MainForecastFragmentBinding.inflate(inflater, viewGroup, attachToRoot)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mainFragmentToolbar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.mainMenuSettings -> {
                    findNavController().navigate(
                        R.id.globalActionToSettingsFragment
                    )
                    true
                }
                else -> false
            }
        }

        binding.mainFragmentTemp.setOnClickListener {
            findNavController().navigate(
                R.id.action_mainForecastFragment_to_detailDialogFragment
            )
        }
    }
}

