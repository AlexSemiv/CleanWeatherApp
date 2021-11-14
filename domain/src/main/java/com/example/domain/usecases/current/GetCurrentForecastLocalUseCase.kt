package com.example.domain.usecases.current

import com.example.common.other.Resource
import com.example.common.other.UseCase
import com.example.domain.models.current.CurrentForecastDomainModel
import com.example.domain.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.Exception

class GetCurrentForecastLocalUseCase @Inject constructor(
    private val repository: Repository
): UseCase<CurrentForecastDomainModel, Nothing> {

    override suspend fun execute(argumentNetwork: Nothing?): Flow<Resource<CurrentForecastDomainModel>> {
        try {
            return repository.getCurrentForecastLocal().flowOn(Dispatchers.IO)
        } catch (e: Exception) {
            return flow { emit(Resource.Error(message = e.message ?: ":(")) }
        }
    }
}