package com.example.local.model.current

data class CurrentForecastLocalModel(
    val current: Current?,
    val daily: List<Daily>?,
    val timezone: String?
)