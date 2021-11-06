package com.example.data.data_models.current

data class MainForecastDataModel(
    val current: Current?,
    val daily: List<Daily>?,
    val hourly: List<Hourly>?,
    val timezone: String?
)