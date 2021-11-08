package com.example.domain.usecases.base

import com.example.common.other.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

abstract class BaseUseCase<DomainModel, Argument> {

    abstract suspend fun buildRequest(argument: Argument?): Flow<Resource<DomainModel>>

    suspend fun execute(argument: Argument?): Flow<Resource<DomainModel>> {
        return try {
            buildRequest(argument)
        } catch (e: Exception) {
            // check Resource.Empty behavior
            flow { Resource.Empty }
        }
    }
}