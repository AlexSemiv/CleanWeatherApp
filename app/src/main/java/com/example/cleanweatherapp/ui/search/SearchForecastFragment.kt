package com.example.cleanweatherapp.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import com.example.cleanweatherapp.R
import com.example.cleanweatherapp.base.BaseFragment
import com.example.cleanweatherapp.databinding.SearchForecastFragmentBinding

class SearchForecastFragment: BaseFragment<SearchForecastFragmentBinding>() {

    override fun bindLayout(
        inflater: LayoutInflater,
        viewGroup: ViewGroup?,
        attachToRoot: Boolean
    ): SearchForecastFragmentBinding {
        return SearchForecastFragmentBinding.inflate(inflater, viewGroup, attachToRoot)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareSearchView()

        binding.searchFragmentToolbar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.searchMenuSettings -> {
                    findNavController().navigate(
                        R.id.globalActionToSettingsFragment
                    )
                    true
                }
                else -> false
            }
        }
    }

    private fun prepareSearchView() {
        val menuItem = binding.searchFragmentToolbar.menu.findItem(R.id.searchMenuSearchForecast)
        val searchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("DEBUG_TAG", newText.toString())
                return false
            }
        })
    }
}