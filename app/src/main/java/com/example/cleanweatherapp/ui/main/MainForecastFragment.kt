package com.example.cleanweatherapp.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.cleanweatherapp.BaseApplication
import com.example.cleanweatherapp.R
import com.example.common.base.BaseFragment
import com.example.cleanweatherapp.databinding.MainForecastFragmentBinding
import com.example.cleanweatherapp.ui.MainActivity
import com.example.presentation.contracts.CurrentContract
import com.example.presentation.models.current.CurrentForecastUiModel
import com.example.presentation.viewmodels.CurrentForecastViewModel
import com.example.presentation.viewmodels.factory.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainForecastFragment : BaseFragment<MainForecastFragmentBinding>() {

    @Inject
    lateinit var factory: ViewModelFactory

    private var viewModel: CurrentForecastViewModel? = null

    override fun bindLayout(
        inflater: LayoutInflater,
        viewGroup: ViewGroup?,
        attachToRoot: Boolean
    ): MainForecastFragmentBinding {
        return MainForecastFragmentBinding.inflate(inflater, viewGroup, attachToRoot)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).activitySubComponent?.injectCurrentForecastFragment(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, factory)[CurrentForecastViewModel::class.java]

        binding.mainFragmentToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.mainMenuSettings -> {
                    findNavController().navigate(
                        R.id.globalActionToSettingsFragment
                    )
                    true
                }
                else -> false
            }
        }

        subscribeObservers()

        if (viewModel?.currentState?.currentForecastState is CurrentContract.CurrentForecastState.Idle)
            viewModel?.setEvent(CurrentContract.Event.OnFetchCurrentForecast)
    }

    private fun subscribeObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel?.uiState?.collect {
                    when (val state = it.currentForecastState) {
                        is CurrentContract.CurrentForecastState.Idle -> {
                            binding.mainFragmentProgressIndicator.isVisible = false
                        }
                        is CurrentContract.CurrentForecastState.Loading -> {
                            binding.mainFragmentProgressIndicator.isVisible = true
                        }
                        is CurrentContract.CurrentForecastState.Success -> {
                            binding.mainFragmentProgressIndicator.isVisible = false
                            val forecast = state.forecast
                            inflateData(forecast)
                            binding.mainFragmentMoreButton.setOnClickListener {
                                viewModel?.setEffect(
                                    CurrentContract.Effect.ShowMoreInfoDialog(
                                        forecast = forecast
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel?.effect?.collect {
                    when (it) {
                        is CurrentContract.Effect.ShowError -> {
                            binding.mainFragmentMoreButton.setOnClickListener(null)
                            val errorView = Snackbar.make(
                                requireView(),
                                it.message ?: "Unknown error",
                                Snackbar.LENGTH_LONG
                            )
                            errorView.setAction("OK") {
                                errorView.dismiss()
                            }.show()
                        }
                        is CurrentContract.Effect.ShowMoreInfoDialog -> {
                            val forecast = it.forecast
                            findNavController().navigate(
                                MainForecastFragmentDirections.actionMainForecastFragmentToDetailDialogFragment(
                                    current = forecast.current
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun inflateData(forecast: CurrentForecastUiModel) {
        Log.d("DEBUG_TAG", "inflateData()")
        Log.d("DEBUG_TAG", forecast.toString())
    }
}

