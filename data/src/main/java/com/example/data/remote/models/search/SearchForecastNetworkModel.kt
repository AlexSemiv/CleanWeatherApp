package com.example.data.remote.models.search

internal data class SearchForecastNetworkModel(
    val city: City?,
    val cnt: Int?,
    val cod: String?,
    val list: List<ThreeHours>?,
    val message: Int?
)