package com.example.data.repositories

import com.example.common.other.Mapper
import com.example.common.other.Resource
import com.example.data.models.current.CurrentForecastDataModel
import com.example.data.models.search.SearchForecastDataModel
import com.example.domain.models.current.CurrentForecastDomainModel
import com.example.domain.models.search.SearchForecastDomainModel
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
    private val currentMapper: Mapper<CurrentForecastDataModel, CurrentForecastDomainModel>,
    private val searchMapper: Mapper<SearchForecastDataModel, SearchForecastDomainModel>
): Repository {

    override suspend fun getCurrentForecastNetwork(
        latitude: Double,
        longitude: Double
    ): Flow<Resource<CurrentForecastDomainModel>> {
        return flow {
            try {
                val localDataForecast = localDataSource.getLastSavedCurrentForecastData()
                emit(Resource.Loading(data = currentMapper.from(localDataForecast)))

                val currentDataForecast = remoteDataSource.getCurrentForecast(
                    latitude = latitude,
                    longitude = longitude,
                    units = localDataSource.getUnits()
                )

                localDataSource.saveCurrentForecastData(currentDataForecast)

                val currentDomainForecast = currentMapper.from(currentDataForecast)
                emit(Resource.Success(data = currentDomainForecast))
            }
            catch (e1: Exception) {
                try {
                    val localDataForecast = localDataSource.getLastSavedCurrentForecastData()
                    emit(Resource.Error(data = currentMapper.from(localDataForecast), message = getErrorMessage(e1)))
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
                emit(Resource.Success(data = currentMapper.from(localDataForecast)))
            } catch (e1: Exception) {
                emit(Resource.Error(message = e1.message ?: ":("))
            }
        }
    }

    override suspend fun getForecastByQuery(query: String): Flow<Resource<SearchForecastDomainModel>> {
        return flow {
            try {
                val searchDataForecast = remoteDataSource.getForecastByQuery(
                    query = query,
                    units = localDataSource.getUnits()
                )
                val searchDomainForecast = searchMapper.from(searchDataForecast)
                emit(Resource.Success(data = searchDomainForecast))
            } catch (e: Exception) {
                emit(Resource.Error(message = getErrorMessage(e)))
            }
        }
    }

    private fun getErrorMessage(e: Exception): String = when(e) {
        is UnknownHostException -> "No internet connection."
        is SocketTimeoutException -> "Server has been unresponsive for a long time."
        is HttpException -> "Server error response."
        else -> e.message ?: ":("
    }
}