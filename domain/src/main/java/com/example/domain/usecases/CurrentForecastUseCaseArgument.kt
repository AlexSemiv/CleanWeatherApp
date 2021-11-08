package com.example.domain.usecases

data class CurrentForecastUseCaseArgument(
    var latitude: Double,
    val longitude: Double,
    val units: String
)