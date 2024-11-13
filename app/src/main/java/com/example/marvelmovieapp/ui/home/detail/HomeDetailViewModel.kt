package com.example.marvelmovieapp.ui.home.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelmovieapp.database.repository.ItemRepository
import com.example.marvelmovieapp.database.model.SavedItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeDetailViewModel @Inject constructor(private val itemRepository: ItemRepository) :
    ViewModel() {
    fun insertItem(item: SavedItem) {
        viewModelScope.launch(Dispatchers.IO) {
            itemRepository.insertItem(item)
        }
    }
}