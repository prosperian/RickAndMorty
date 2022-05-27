package com.dip.rickandmorty.api

import com.dip.rickandmorty.models.Character
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {

    @GET("character")
    suspend fun getCharacters(): Response<ApiResponse<MutableList<Character>>>

    @GET("character")
    suspend fun searchCharacter(
        @Query("page") page: String = "1",
        @Query("name") name: String,
    ): Response<ApiResponse<MutableList<Character>>>

    @GET
    suspend fun loadNextPage(
        @Url nextPageUrl: String,
    ): Response<ApiResponse<MutableList<Character>>>
}