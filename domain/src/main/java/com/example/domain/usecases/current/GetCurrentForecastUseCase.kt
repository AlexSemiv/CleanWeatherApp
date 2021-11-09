package com.example.domain.usecases.current

import com.example.common.other.Resource
import com.example.common.other.UseCase
import com.example.domain.models.current.CurrentForecastDomainModel
import com.example.domain.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject

class GetCurrentForecastUseCase @Inject constructor(
    private val repository: Repository
): UseCase<CurrentForecastDomainModel, CurrentForecastUseCaseArgument> {

    override suspend fun execute(argument: CurrentForecastUseCaseArgument?): Flow<Resource<CurrentForecastDomainModel>> {
        return try {
            argument?.let { arg ->
                return repository.getCurrentForecast(
                    latitude = arg.latitude,
                    longitude = arg.longitude
                ).flowOn(Dispatchers.IO)
            }
            return flow {
                emit(Resource.Error(message = "Argument in useCase is null"))
            }
        } catch (e: Exception) {
            // check Resource.Empty behavior
            flow { Resource.Empty }
        }
    }
}