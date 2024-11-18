package com.example.marvelmovieapp.network.model

data class MarvelResponse<T>(
    val data: MarvelData<T>
)

data class MarvelData<T>(
    val results: List<T>
)