package com.dip.rickandmorty.modules

import com.dip.rickandmorty.api.ApiService
import com.dip.rickandmorty.repositories.NetworkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MainRepositoryModule {

    @Provides
    @Singleton
    fun provideNetworkRepository(apiService: ApiService): NetworkRepository {
        return NetworkRepository(apiService)
    }

}