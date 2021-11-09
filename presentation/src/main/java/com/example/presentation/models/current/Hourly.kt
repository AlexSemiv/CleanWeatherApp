package com.example.presentation.models.current

import java.io.Serializable

data class Hourly(
    val dt: Int?,
    val feels_like: Double?,
    val temp: Double?
): Serializable