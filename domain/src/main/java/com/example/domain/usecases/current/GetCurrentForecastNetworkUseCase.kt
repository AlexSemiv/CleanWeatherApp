package com.example.domain.usecases.current

import com.example.common.other.Resource
import com.example.common.base.UseCase
import com.example.domain.models.current.CurrentForecastDomainModel
import com.example.domain.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import java.lang.IllegalArgumentException
import javax.inject.Inject

class GetCurrentForecastNetworkUseCase @Inject constructor(
    private val repository: Repository
): UseCase<CurrentForecastDomainModel, CurrentForecastNetworkUseCaseArgument> {

    override suspend fun execute(argumentNetwork: CurrentForecastNetworkUseCaseArgument?): Flow<Resource<CurrentForecastDomainModel>> {
        try {
            argumentNetwork?.let { arg ->
                return repository.getCurrentForecastNetwork(
                    latitude = requireNotNull(arg.latitude),
                    longitude = requireNotNull(arg.longitude)
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