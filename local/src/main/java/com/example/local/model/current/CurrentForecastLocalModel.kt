package com.example.local.model.current

data class CurrentForecastLocalModel(
    val current: Current?,
    val daily: List<Daily>?,
    val hourly: List<Hourly>?,
    val timezone: String?
)