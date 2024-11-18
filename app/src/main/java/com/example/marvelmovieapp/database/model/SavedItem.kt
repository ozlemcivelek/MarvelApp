package com.example.marvelmovieapp.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_items")
data class SavedItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val itemId: Int,
    val type: String,
    val title: String,
    val description: String,
    val imageUrl: String
)
