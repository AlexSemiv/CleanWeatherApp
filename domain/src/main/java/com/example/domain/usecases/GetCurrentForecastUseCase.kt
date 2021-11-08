package com.example.domain.usecases

import com.example.common.other.Resource
import com.example.domain.models.current.CurrentForecastDomainModel
import com.example.domain.repository.Repository
import com.example.domain.usecases.base.BaseUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetCurrentForecastUseCase @Inject constructor(
    private val repository: Repository
): BaseUseCase<CurrentForecastDomainModel, CurrentForecastUseCaseArgument>() {

    override suspend fun buildRequest(argument: CurrentForecastUseCaseArgument?): Flow<Resource<CurrentForecastDomainModel>> {
        argument?.let { arg ->
            return repository.getCurrentForecast(
                latitude = arg.latitude,
                longitude = arg.longitude,
                units = arg.units
            ).flowOn(Dispatchers.IO)
        }
        return flow {
            emit(Resource.Error(message = "Argument in useCase is null"))
        }
    }
}