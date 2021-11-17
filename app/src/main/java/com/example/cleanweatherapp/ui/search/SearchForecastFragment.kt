package com.example.cleanweatherapp.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleanweatherapp.R
import com.example.common.base.BaseFragment
import com.example.cleanweatherapp.databinding.SearchForecastFragmentBinding
import com.example.cleanweatherapp.ui.MainActivity
import com.example.presentation.contracts.SearchContract
import com.example.presentation.viewmodels.SearchForecastViewModel
import com.example.presentation.viewmodels.factory.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class SearchForecastFragment: BaseFragment<SearchForecastFragmentBinding>() {

    @Inject
    lateinit var factory: ViewModelFactory

    @Inject
    lateinit var threeHoursAdapter: ThreeHoursAdapter

    private var viewModel: SearchForecastViewModel? = null

    private var searchJob: Job? = null

    override fun bindLayout(
        inflater: LayoutInflater,
        viewGroup: ViewGroup?,
        attachToRoot: Boolean
    ): SearchForecastFragmentBinding {
        return SearchForecastFragmentBinding.inflate(inflater, viewGroup, attachToRoot)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).activitySubComponent?.injectSearchForecastFragment(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, factory)[SearchForecastViewModel::class.java]

        viewModel?.internetConnectionLiveData?.observe(viewLifecycleOwner) { hasConnection ->
            if(hasConnection)
                binding.toolbar.navigationIcon = null
            else
                binding.toolbar.setNavigationIcon(R.drawable.ic_wifi_off)
        }

        prepareSearchView()

        subscribeToObservers()

        binding.searchFragmentRecyclerView.apply {
            adapter = threeHoursAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun subscribeToObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel?.uiState?.collect {
                    when(val dataState = it.searchForecastState) {
                        is SearchContract.SearchForecastState.Idle -> {
                            binding.searchFragmentProgressIndicator.isVisible = false
                        }
                        is SearchContract.SearchForecastState.Loading -> {
                            binding.searchFragmentProgressIndicator.isVisible = true
                        }
                        is SearchContract.SearchForecastState.Success -> {
                            val forecast = dataState.forecast
                            threeHoursAdapter.submitList(forecast.list)
                            binding.searchFragmentProgressIndicator.isVisible = false
                        }
                        is SearchContract.SearchForecastState.Error -> {
                            threeHoursAdapter.submitList(listOf())
                            binding.searchFragmentProgressIndicator.isVisible = false
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel?.effect?.collect {
                    when(it) {
                        is SearchContract.Effect.ShowError -> {
                            Snackbar.make(
                                requireView(),
                                it.message ?: "Unknown error",
                                Snackbar.LENGTH_LONG
                            ).apply {
                                anchorView = (activity as MainActivity).binding.bottomNavigationView
                                setAction("OK") {
                                    this.dismiss()
                                }
                            }.show()
                        }
                    }
                }
            }
        }
    }

    private fun prepareSearchView() {
        val menuItem = binding.toolbar.menu.findItem(R.id.searchMenuSearchForecast)
        val searchView = menuItem.actionView as SearchView
        searchView.apply {

            var shouldSearch = false

            setOnQueryTextFocusChangeListener { _, hasFocus ->
                shouldSearch = hasFocus
            }

            setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if(shouldSearch) {
                        searchJob?.cancel()
                        searchJob = lifecycleScope.launch(Dispatchers.IO) {
                            if(isActive) {
                                query?.let { query ->
                                    if(query.length > 1) {
                                        viewModel?.setEvent(SearchContract.Event.OnFetchSearchForecastNetwork(
                                            query = query
                                        ))
                                    }
                                }
                            }
                        }
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean = false
            })
        }
    }
}