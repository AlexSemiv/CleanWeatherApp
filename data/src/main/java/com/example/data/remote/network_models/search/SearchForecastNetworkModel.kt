package com.example.data.remote.network_models.search

data class SearchForecastNetworkModel(
    val city: City?,
    val cnt: Int?,
    val cod: String?,
    val list: List<ThreeHours>?,
    val message: Int?
)