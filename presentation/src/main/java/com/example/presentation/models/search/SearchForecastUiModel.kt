package com.example.presentation.models.search

data class SearchForecastUiModel(
    val city: City?,
    val list: List<ThreeHours>?
)