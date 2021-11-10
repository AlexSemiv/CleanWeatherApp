package com.example.domain.models.current

data class CurrentForecastDomainModel(
    val current: Current?,
    val daily: List<Daily>?,
    val timezone: String?
)