package com.example.presentation.models.current

import java.io.Serializable

data class CurrentForecastUiModel(
    val current: Current?,
    val daily: List<Daily>?,
    val hourly: List<Hourly>?,
    val timezone: String?
) : Serializable