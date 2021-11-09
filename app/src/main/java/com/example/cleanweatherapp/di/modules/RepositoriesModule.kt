package com.example.cleanweatherapp.di.modules

import com.example.data.repositories.LocalDataSource
import com.example.data.repositories.RemoteDataSource
import com.example.data.repositories.RepositoryImpl
import com.example.domain.qualifiers.ActivityScope
import com.example.domain.repository.Repository
import com.example.local.source.LocalDataSourceImp
import com.example.remote.sources.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoriesModule {

    @ActivityScope
    @Binds
    abstract fun bindsRemoteDataSource(
        remoteDataSource: RemoteDataSourceImpl
    ): RemoteDataSource

    @ActivityScope
    @Binds
    abstract fun bindsLocalDataSource(
        localDataSource: LocalDataSourceImp
    ): LocalDataSource

    @ActivityScope
    @Binds
    abstract fun bindsRepository(
        repositoryImpl: RepositoryImpl
    ): Repository
}