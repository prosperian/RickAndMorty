package com.dip.rickandmorty.repositories

import androidx.annotation.WorkerThread
import com.dip.rickandmorty.api.ApiResponse
import com.dip.rickandmorty.api.ApiService
import com.dip.rickandmorty.models.Character
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Response
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class NetworkRepository @Inject constructor(private val apiService: ApiService) {

    @WorkerThread
    suspend fun getCharacters(): Response<ApiResponse<MutableList<Character>>> {
        return apiService.getCharacters()
    }

    @WorkerThread
    suspend fun searchCharacter(name: String): Response<ApiResponse<MutableList<Character>>> {
        return apiService.searchCharacter(name = name)
    }

    @WorkerThread
    suspend fun nextPage(url: String): Response<ApiResponse<MutableList<Character>>> {
        return apiService.loadNextPage(url)
    }
}