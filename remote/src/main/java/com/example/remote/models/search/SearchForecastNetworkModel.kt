package com.example.remote.models.search

data class SearchForecastNetworkModel(
    val city: City?,
    val cnt: Int?,
    val cod: String?,
    val list: List<ThreeHours>?,
    val message: Int?
)