package com.example.marvelmovieapp.network.model

data class ComicResponse(
    val id: Int,
    val title: String,
    val description: String,
    val thumbnail:Thumbnail,
)
