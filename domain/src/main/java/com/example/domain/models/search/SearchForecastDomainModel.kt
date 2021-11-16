package com.example.domain.models.search

data class SearchForecastDomainModel(
    val city: City?,
    val list: List<ThreeHours>?
)