package com.example.data.models.current

data class CurrentForecastDataModel(
    val current: Current?,
    val daily: List<Daily>?,
    val timezone: String?
)