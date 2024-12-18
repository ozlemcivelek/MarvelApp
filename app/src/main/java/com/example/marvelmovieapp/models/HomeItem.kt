package com.example.marvelmovieapp.models

import java.io.Serializable

data class HomeItem(
    val id: Int,
    val type: String,
    val imageUrl: String,
    val imageTitle: String,
    val description: String,
) : Serializable
