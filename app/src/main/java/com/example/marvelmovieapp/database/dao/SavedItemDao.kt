package com.example.marvelmovieapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.marvelmovieapp.database.model.SavedItem

@Dao
interface SavedItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItem(item: SavedItem)

    @Query("SELECT * FROM saved_items") // ORDER BY id ASC
    fun getAllItems(): LiveData<List<SavedItem>> // LiveData ile library sayfasında dinamik gösterim

    @Delete
    suspend fun deleteItem(item: SavedItem)

    @Query("DELETE FROM saved_items WHERE itemId = :id")
    suspend fun deleteItemById(id: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM saved_items WHERE itemId = :id)")
    suspend fun isItemExist(id: Int): Boolean
}
