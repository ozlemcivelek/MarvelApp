package com.example.marvelmovieapp.di

import com.example.marvelmovieapp.database.dao.SavedItemDao
import com.example.marvelmovieapp.database.repository.ItemRepository
import com.example.marvelmovieapp.database.repository.ItemRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    fun provideItemRepository(
        savedItemDao: SavedItemDao
    ): ItemRepository = ItemRepositoryImpl(savedItemDao)
}