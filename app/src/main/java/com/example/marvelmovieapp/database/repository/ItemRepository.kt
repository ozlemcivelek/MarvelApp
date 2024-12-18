package com.example.marvelmovieapp.database.repository

import androidx.lifecycle.LiveData
import com.example.marvelmovieapp.database.model.SavedItem

interface ItemRepository {
    suspend fun getAllItems(type: String): List<SavedItem>
    suspend fun insertItem(item: SavedItem)
    suspend fun deleteItem(id: Int)
    suspend fun isItemExist(id: Int) : Boolean
}