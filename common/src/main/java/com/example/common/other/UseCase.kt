package com.example.common.other

import kotlinx.coroutines.flow.Flow

interface UseCase<DomainModel, Argument> {
    suspend fun execute(argument: Argument?): Flow<Resource<DomainModel>>
}