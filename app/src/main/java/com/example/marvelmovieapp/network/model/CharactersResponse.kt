package com.example.marvelmovieapp.network.model

data class CharactersResponse(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: Thumbnail,
)
