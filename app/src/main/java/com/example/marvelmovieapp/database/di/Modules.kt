package com.example.marvelmovieapp.database.di

import com.example.marvelmovieapp.database.AppDatabase
import com.example.marvelmovieapp.database.dao.SavedItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    fun providesSavedItemDao(
        database: AppDatabase,
    ): SavedItemDao = database.savedItemDao()
}