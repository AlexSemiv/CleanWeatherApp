package com.example.presentation.models.current

data class CurrentForecastUiModel(
    val current: Current?,
    val daily: List<Daily>?,
    val timezone: String?
)