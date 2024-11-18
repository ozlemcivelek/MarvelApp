package com.example.marvelmovieapp.network.model

data class EventsResponse (
    val id: Int,
    val title: String,
    val description: String,
    val start: String,
    val end: String,
    val thumbnail: Thumbnail
)