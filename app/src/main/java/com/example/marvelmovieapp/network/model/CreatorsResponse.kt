package com.example.marvelmovieapp.network.model

data class CreatorsResponse(
    val id: Int,
    val firstName: String,
    val fullName: String,
    val thumbnail: Thumbnail,
    val modified: String,
)
