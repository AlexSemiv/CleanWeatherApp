package com.example.domain.usecases.current

data class CurrentForecastUseCaseArgument(
    var latitude: Double,
    val longitude: Double,
    val units: String
)