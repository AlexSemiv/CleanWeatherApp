package com.example.domain.repository

import com.example.common.other.Resource
import com.example.domain.models.current.CurrentForecastDomainModel
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getCurrentForecastNetwork(
        latitude: Double,
        longitude: Double
    ): Flow<Resource<CurrentForecastDomainModel>>

    suspend fun getCurrentForecastLocal(

    ): Flow<Resource<CurrentForecastDomainModel>>
}