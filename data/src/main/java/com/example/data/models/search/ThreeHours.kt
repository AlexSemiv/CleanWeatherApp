package com.example.data.models.search

data class ThreeHours(
    val clouds: Clouds?,
    val dt: Int?,
    val dt_txt: String?,
    val main: Main?,
    val rain: Rain?,
    val snow: Snow?,
    val visibility: Int?,
    val weather: Weather?,
    val wind: Wind?
)