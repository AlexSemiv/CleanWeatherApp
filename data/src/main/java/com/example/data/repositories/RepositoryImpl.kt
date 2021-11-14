package com.example.data.repositories

import com.example.common.other.Mapper
import com.example.common.other.Resource
import com.example.data.models.current.CurrentForecastDataModel
import com.example.domain.models.current.CurrentForecastDomainModel
import com.example.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.lang.Exception
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val mapper: Mapper<CurrentForecastDataModel, CurrentForecastDomainModel>
): Repository {

    override suspend fun getCurrentForecastNetwork(
        latitude: Double,
        longitude: Double
    ): Flow<Resource<CurrentForecastDomainModel>> {
        return flow {
            try {
                val localDataForecast = localDataSource.getLastSavedCurrentForecastData()
                emit(Resource.Loading(data = mapper.from(localDataForecast)))

                val currentDataForecast = remoteDataSource.getCurrentForecast(
                    latitude = latitude,
                    longitude = longitude,
                    units = localDataSource.getUnits()
                )

                localDataSource.saveCurrentForecastData(currentDataForecast)

                val currentDomainForecast = mapper.from(currentDataForecast)
                emit(Resource.Success(data = currentDomainForecast))
            }
            catch (e1: Exception) {
                try {
                    val localDataForecast = localDataSource.getLastSavedCurrentForecastData()
                    when (e1) {
                        is UnknownHostException -> {
                            emit(Resource.Error(data = mapper.from(localDataForecast), message = "No internet connection."))
                        }
                        is SocketTimeoutException -> {
                            emit(Resource.Error(data = mapper.from(localDataForecast), message = "Server has been unresponsive for a long time."))
                        }
                        is HttpException -> {
                            emit(Resource.Error(data = mapper.from(localDataForecast), message = "Server error response."))
                        }
                        else -> {
                            emit(Resource.Error(data = mapper.from(localDataForecast), message = e1.message ?: ":("))
                        }
                    }
                } catch (e2: Exception) {
                    emit(Resource.Error(message = e2.message ?: ":("))
                }
            }
        }
    }

    override suspend fun getCurrentForecastLocal(): Flow<Resource<CurrentForecastDomainModel>> {
        return flow {
            try {
                val localDataForecast = localDataSource.getLastSavedCurrentForecastData()
                emit(Resource.Success(data = mapper.from(localDataForecast)))
            } catch (e1: Exception) {
                emit(Resource.Error(message = e1.message ?: ":("))
            }
        }
    }
}