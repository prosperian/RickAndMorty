package com.dip.rickandmorty.api

import com.dip.rickandmorty.models.Info

data class ApiResponse<T>(
    val info: Info,
    val results: T
)