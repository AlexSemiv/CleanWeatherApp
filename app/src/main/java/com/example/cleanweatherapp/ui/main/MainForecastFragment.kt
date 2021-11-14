package com.example.cleanweatherapp.ui.main

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleanweatherapp.R
import com.example.common.base.BaseFragment
import com.example.cleanweatherapp.databinding.MainForecastFragmentBinding
import com.example.cleanweatherapp.ui.MainActivity
import com.example.common.other.Constants
import com.example.common.other.Constants.toFormattedDescription
import com.example.common.other.Constants.toFormattedHoursAndMinutes
import com.example.common.other.Constants.toFormattedTitle
import com.example.presentation.contracts.CurrentContract
import com.example.presentation.livedata.CurrentLocationLiveData
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

    @Inject
    lateinit var dailyAdapter: DailyAdapter

    @Inject
    lateinit var currentLocationLiveData: CurrentLocationLiveData

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

        subscribeObservers(savedInstanceState)

        if (viewModel?.currentState?.currentForecastState == CurrentContract.CurrentForecastState.Idle)
            viewModel?.setEvent(CurrentContract.Event.OnFetchCurrentForecastLocal)

        if (viewModel?.currentState?.currentPermissionsState == CurrentContract.CurrentPermissionState.Idle)
            activityResultLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )

        (activity as MainActivity).setOnUnitsChangeListener {
            if (viewModel?.currentState?.currentPermissionsState == CurrentContract.CurrentPermissionState.PermissionsGranted)
                viewModel?.setEvent(CurrentContract.Event.OnFetchCurrentForecastNetwork)
        }

        binding.srlRefresh.apply {
            setOnRefreshListener {
                if (viewModel?.currentState?.currentPermissionsState == CurrentContract.CurrentPermissionState.PermissionsGranted)
                    viewModel?.setEvent(CurrentContract.Event.OnFetchCurrentForecastNetwork)
                else
                    viewModel?.setEvent(CurrentContract.Event.OnFetchCurrentForecastLocal)
                isRefreshing = false
            }
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

        binding.rvDaily.apply {
            adapter = dailyAdapter.apply {
                setOnItemClickListener { dailyForecast ->
                    viewModel?.setEffect(
                        CurrentContract.Effect.ShowMoreInfoDailyDialog(
                            dailyForecast
                        )
                    )
                }
            }
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.HORIZONTAL
                )
            )
        }
    }

    private val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { map ->
        if (map.entries.all { it.value }) {
            viewModel?.setEvent(
                CurrentContract.Event.OnFetchCurrentForecastNetwork
            )
            viewModel?.setEvent(
                CurrentContract.Event.OnChangePermissionsState(
                    newState = CurrentContract.CurrentPermissionState.PermissionsGranted
                )
            )
        } else {
            if (shouldShowDialogDuePermission(Manifest.permission.ACCESS_FINE_LOCATION) ||
                shouldShowDialogDuePermission(Manifest.permission.ACCESS_COARSE_LOCATION)
            )
                viewModel?.setEvent(
                    CurrentContract.Event.OnChangePermissionsState(
                        newState = CurrentContract.CurrentPermissionState.PermissionsPermanentlyDenied
                    )
                )
            else
                viewModel?.setEvent(
                    CurrentContract.Event.OnChangePermissionsState(
                        newState = CurrentContract.CurrentPermissionState.PermissionsDenied
                    )
                )
        }
    }

    private fun shouldShowDialogDuePermission(permission: String) =
        !ActivityCompat.shouldShowRequestPermissionRationale(
            requireActivity(),
            permission
        )

    private fun subscribeObservers(savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel?.uiState?.collect {
                    when (it.currentPermissionsState) {
                        is CurrentContract.CurrentPermissionState.Idle -> Unit
                        is CurrentContract.CurrentPermissionState.PermissionsGranted -> {
                            currentLocationLiveData.observe(viewLifecycleOwner) { location ->
                                Log.d("DEBUG_TAG", location.toString())
                            }
                        }
                        is CurrentContract.CurrentPermissionState.PermissionsDenied -> {
                            currentLocationLiveData.removeObservers(viewLifecycleOwner)
                        }
                        is CurrentContract.CurrentPermissionState.PermissionsPermanentlyDenied -> {
                            currentLocationLiveData.removeObservers(viewLifecycleOwner)
                            if (savedInstanceState == null)
                                viewModel?.setEffect(CurrentContract.Effect.ShowPermissionsRequiredDialog)
                        }
                    }

                    when (val dataState = it.currentForecastState) {
                        is CurrentContract.CurrentForecastState.Idle -> {
                            binding.mainFragmentProgressIndicator.isVisible = false
                        }
                        is CurrentContract.CurrentForecastState.Loading -> {
                            binding.mainFragmentProgressIndicator.isVisible = true
                            val cashedForecast = dataState.cashedForecast
                            dailyAdapter.submitList(cashedForecast?.daily)
                            inflateData(cashedForecast)
                        }
                        is CurrentContract.CurrentForecastState.Success -> {
                            val forecast = dataState.forecast
                            dailyAdapter.submitList(forecast.daily)
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
                        is CurrentContract.Effect.ShowMoreInfoCurrentDialog -> {
                            val forecast = it.forecast
                            findNavController().navigate(
                                MainForecastFragmentDirections.actionMainForecastFragmentToDetailDialogFragment(
                                    current = forecast.current
                                )
                            )
                        }
                        is CurrentContract.Effect.ShowMoreInfoDailyDialog -> {
                            val daily = it.daily
                            findNavController().navigate(
                                MainForecastFragmentDirections.actionMainForecastFragmentToItemDialogFragment(
                                    daily
                                )
                            )
                        }
                        is CurrentContract.Effect.ShowPermissionsRequiredDialog -> {
                            findNavController().navigate(
                                MainForecastFragmentDirections.actionMainForecastFragmentToPermissionDialog()
                            )
                        }
                    }
                }
            }
        }
    }

    private fun inflateData(forecast: CurrentForecastUiModel?) = with(binding) {
        if (forecast == null)
            return

        (forecast.timezone?.toFormattedTitle()).also { toolbar.title = it }
        ("last update " + forecast.current?.dt?.toFormattedHoursAndMinutes()).also {
            toolbar.subtitle = it
        }
        (forecast.current?.humidity.toString() + "%").also { tvHumidity.text = it }
        (forecast.current?.pressure.toString() + "mBar").also { tvPressure.text = it }
        (forecast.current?.wind_speed.toString() + Constants.UNITS_OF_MEASUREMENT_WIND_SPEED).also {
            tvWindSpeed.text = it
        }
        (forecast.current?.weatherCurrent?.description?.toFormattedDescription()).also {
            tvDescription.text = it
        }
        (forecast.current?.temp?.toInt()
            .toString() + Constants.UNITS_OF_MEASUREMENT_TEMP).also { tvTemp.text = it }
        (forecast.current?.sunrise?.toFormattedHoursAndMinutes()).also { tvSunrise.text = it }
        (forecast.current?.sunset?.toFormattedHoursAndMinutes()).also { tvSunset.text = it }

        btnShowInfoDialog.setOnClickListener {
            viewModel?.setEffect(
                CurrentContract.Effect.ShowMoreInfoCurrentDialog(
                    forecast = forecast
                )
            )
        }
    }
}