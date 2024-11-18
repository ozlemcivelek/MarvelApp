package com.example.marvelmovieapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.marvelmovieapp.database.dao.SavedItemDao
import com.example.marvelmovieapp.database.model.SavedItem

@Database(entities = [SavedItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun savedItemDao(): SavedItemDao
}
