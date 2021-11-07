package com.example.cleanweatherapp.di.modules

import com.example.data.repositories.RemoteDataSource
import com.example.data.repositories.RepositoryImpl
import com.example.domain.repository.Repository
import com.example.remote.sources.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoriesModule {

    @Binds
    abstract fun bindsRemoteDataSource(
        remoteDataSource: RemoteDataSourceImpl
    ): RemoteDataSource

    @Binds
    abstract fun bindsRepository(
        repositoryImpl: RepositoryImpl
    ): Repository
}