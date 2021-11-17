package com.example.common.base

import com.example.common.other.Resource
import kotlinx.coroutines.flow.Flow

interface UseCase<DomainModel, Argument> {
    suspend fun execute(argument: Argument?): Flow<Resource<DomainModel>>
}