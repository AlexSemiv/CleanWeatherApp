package com.example.data.remote.models.search

internal data class ThreeHours(
    val clouds: Clouds?,
    val dt: Int?,
    val dt_txt: String?,
    val main: Main?,
    val pop: Int?,
    val rain: Rain?,
    val snow: Snow?,
    val sys: Sys?,
    val visibility: Int?,
    val weather: List<Weather>?,
    val wind: Wind?
)