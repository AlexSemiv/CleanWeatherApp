package com.example.data.remote.models.current

internal data class Hourly(
    val clouds: Int?,
    val dew_point: Double?,
    val dt: Int?,
    val feels_like: Double?,
    val humidity: Int?,
    val pop: Int?,
    val pressure: Int?,
    val snow: Snow?,
    val temp: Double?,
    val uvi: Int?,
    val visibility: Int?,
    val weather: List<Weather>?,
    val wind_deg: Int?,
    val wind_gust: Double?,
    val wind_speed: Double?
)