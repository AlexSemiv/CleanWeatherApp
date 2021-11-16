package com.example.domain.usecases.search

import com.example.common.other.Resource
import com.example.common.other.UseCase
import com.example.domain.models.search.SearchForecastDomainModel
import com.example.domain.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import java.lang.IllegalArgumentException
import javax.inject.Inject

class GetSearchForecastNetworkUseCase @Inject constructor(
    private val repository: Repository
) : UseCase<SearchForecastDomainModel, SearchForecastNetworkUseCaseArgument> {

    override suspend fun execute(argument: SearchForecastNetworkUseCaseArgument?): Flow<Resource<SearchForecastDomainModel>> {
        try {
            argument?.let { arg ->
                return repository.getForecastByQuery(
                    query = requireNotNull(arg.query)
                ).flowOn(Dispatchers.IO)
            }
            return flow {
                emit(Resource.Error(message = "Argument in useCase is null"))
            }
        }
        catch (e: IllegalArgumentException) {
            return flow { emit(Resource.Error(message = "App can't find your current location. Try again later, please.")) }
        }
        catch (e: Exception) {
            return flow { emit(Resource.Error(message = e.message ?: "")) }
        }
    }
}