package com.example.presentation.models.current

data class Daily(
    val clouds: Int?,
    val dew_point: Double?,
    val dt: Int?,
    val feels_like: Double?,
    val humidity: Int?,
    val pressure: Int?,
    val rain: Double?,
    val snow: Double?,
    val sunrise: Int?,
    val sunset: Int?,
    val temp: Double?,
    val uvi: Double?,
    val weather: Weather?,
    val wind_deg: Int?,
    val wind_gust: Double?,
    val wind_speed: Double?
)