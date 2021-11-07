package com.example.domain.repository

import com.example.common.other.Resource
import com.example.domain.models.current.CurrentForecastDomainModel
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getCurrentForecast(
        latitude: Double,
        longitude: Double,
        units: String
    ): Flow<Resource<CurrentForecastDomainModel>>
}