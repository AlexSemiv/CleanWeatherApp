package com.example.cleanweatherapp.ui.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.cleanweatherapp.R
import com.example.common.base.BaseFragment
import com.example.cleanweatherapp.databinding.MainForecastFragmentBinding
import com.example.cleanweatherapp.ui.MainActivity
import com.example.common.other.ConvertFunctions
import com.example.presentation.contracts.CurrentContract
import com.example.presentation.models.current.CurrentForecastUiModel
import com.example.presentation.models.current.Daily
import com.example.presentation.viewmodels.CurrentForecastViewModel
import com.example.presentation.viewmodels.factory.ViewModelFactory
import com.github.mikephil.charting.data.Entry
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

        (activity as MainActivity).setOnUnitsChangeListener {
            viewModel?.setEvent(CurrentContract.Event.OnFetchCurrentForecastNetwork)
        }

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
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

        binding.srlRefresh.apply {
            setOnRefreshListener {
                viewModel?.setEvent(CurrentContract.Event.OnFetchCurrentForecastNetwork)
                isRefreshing = false
            }
        }

        subscribeObservers()

        if (viewModel?.currentState?.currentForecastState is CurrentContract.CurrentForecastState.Idle)
            viewModel?.setEvent(CurrentContract.Event.OnFetchCurrentForecastNetwork)
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
                            val cashedForecast = state.cashedForecast
                            inflateData(cashedForecast)
                        }
                        is CurrentContract.CurrentForecastState.Success -> {
                            val forecast = state.forecast
                            inflateData(forecast)
                            binding.mainFragmentProgressIndicator.isVisible = false
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
                            binding.btnShowInfoDialog.setOnClickListener(null)
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

    private fun inflateData(forecast: CurrentForecastUiModel?) {
        if(forecast == null)
            return

        with(binding) {
            btnShowInfoDialog.setOnClickListener {
                viewModel?.setEffect(
                    CurrentContract.Effect.ShowMoreInfoDialog(
                        forecast = forecast
                    )
                )
            }
            toolbar.title = ConvertFunctions.formattedTitle(forecast.timezone!!)
            toolbar.subtitle = "last update ${ConvertFunctions.formattedTime(forecast.current?.dt ?: 0)}"
            tvHumidity.text = "${forecast.current?.humidity}%"
            tvPressure.text = "${forecast.current?.pressure}mBar"
            tvWindSpeed.text = "${forecast.current?.wind_speed}m/sec"
            lottieIcon.setAnimation(when(forecast.current?.weatherCurrent?.icon) {
                "01d" -> R.raw.clear_sky_day
                "01n" -> R.raw.clear_sky_night
                "02d", "03d", "04d" -> R.raw.cloudy_day
                "02n", "03n", "04n" -> R.raw.cloudy_night
                "09d", "10d" -> R.raw.rain_day
                "09n", "10n" -> R.raw.rain_night
                "11d", "11n" -> R.raw.thunder
                "13d" -> R.raw.snow_day
                "13n" -> R.raw.snow_night
                "50d" -> R.raw.mist_day
                "50n" -> R.raw.mist_night
                else -> R.raw.clear_sky_day
            })
            tvDescription.text = ConvertFunctions.formattedDescription(forecast.current?.weatherCurrent?.description ?: "Description")
            tvTemp.text = "${forecast.current?.temp?.toInt()}°"
            tvSunset.text = ConvertFunctions.formattedTime(forecast.current?.sunset ?: 0)
            tvSunrise.text = ConvertFunctions.formattedTime(forecast.current?.sunrise ?: 0)

            // TODO: 09.11.2021 сделать график как в фитнес треккере
            /*val lineEntry = forecast.daily?.dailyToEntry()
            val lineDataSet = LineDataSet(lineEntry, "First")
            lineDataSet.color = resources.getColor(R.color.black)
            val data = LineData(lineDataSet)
            lineChart.data = data
            lineChart.background = null*/
        }
    }
}

