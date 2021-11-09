package com.example.presentation.models.current

import java.io.Serializable

data class Daily(
    val dt: Int?,
    val feels_like: Double?,
    val temp: Double?
) : Serializable