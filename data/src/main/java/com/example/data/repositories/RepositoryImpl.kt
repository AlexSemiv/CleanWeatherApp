package com.example.data.repositories

import com.example.common.other.Mapper
import com.example.common.other.Resource
import com.example.data.models.current.CurrentForecastDataModel
import com.example.domain.models.current.CurrentForecastDomainModel
import com.example.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val mapper: Mapper<CurrentForecastDataModel, CurrentForecastDomainModel>
): Repository {

    override suspend fun getCurrentForecast(
        latitude: Double,
        longitude: Double,
        units: String
    ): Flow<Resource<CurrentForecastDomainModel>> {
        return flow {

        }
    }
}