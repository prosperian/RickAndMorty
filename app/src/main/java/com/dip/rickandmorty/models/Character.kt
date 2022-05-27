package com.dip.rickandmorty.models

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val gender: String,
    val species: String,
    val image: String
)