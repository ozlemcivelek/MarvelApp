package com.example.marvelmovieapp.network.model

data class SeriesResponse(
    val id: Int,
    val title: String,
    val description: String,
    val thumbnail:Thumbnail
)
