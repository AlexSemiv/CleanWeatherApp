package com.example.cleanweatherapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cleanweatherapp.R
import com.example.cleanweatherapp.base.BaseFragment
import com.example.cleanweatherapp.databinding.MainForecastDialogBinding
import com.example.cleanweatherapp.databinding.MainForecastFragmentBinding
import com.example.cleanweatherapp.ui.MainActivity

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

