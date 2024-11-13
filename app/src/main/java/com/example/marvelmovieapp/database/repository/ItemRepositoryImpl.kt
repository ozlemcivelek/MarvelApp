package com.example.marvelmovieapp.database.repository

import androidx.lifecycle.LiveData
import com.example.marvelmovieapp.database.dao.SavedItemDao
import com.example.marvelmovieapp.database.model.SavedItem

class ItemRepositoryImpl(private val savedItemDao: SavedItemDao) : ItemRepository {

    override suspend fun getAllItems(): LiveData<List<SavedItem>> {
        return savedItemDao.getAllItems()
    }

    override suspend fun insertItem(item: SavedItem) {
        savedItemDao.insertItem(item)
    }

    override suspend fun deleteItem(id: Int) {
        savedItemDao.deleteItemById(id)
    }

    override suspend fun isItemExist(id: Int): Boolean {
       return savedItemDao.isItemExist(id)
    }
}