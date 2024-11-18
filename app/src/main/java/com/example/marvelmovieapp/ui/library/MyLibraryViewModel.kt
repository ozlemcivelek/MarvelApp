package com.example.marvelmovieapp.ui.library

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelmovieapp.database.repository.ItemRepository
import com.example.marvelmovieapp.models.HomeItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyLibraryViewModel @Inject constructor(private val itemRepository: ItemRepository) :
    ViewModel() {
    val event = MutableLiveData<List<HomeItem>>()
    val comic = MutableLiveData<List<HomeItem>>()
    val creator = MutableLiveData<List<HomeItem>>()
    val character = MutableLiveData<List<HomeItem>>()

    fun getAllItems(type: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val items = itemRepository.getAllItems(type)
            val homeItems = items.map { item ->
                HomeItem(
                    id = item.itemId,
                    type = type,
                    imageUrl = item.imageUrl,
                    imageTitle = item.title,
                    description = item.description
                )
            }
            homeItemListByTypes(type, homeItems)
        }
    }

    private fun homeItemListByTypes(type: String, homeItems: List<HomeItem>){
       when (type){
           "EVENTS" -> event.postValue(homeItems)
           "COMICS" -> comic.postValue(homeItems)
           "CREATORS" -> creator.postValue(homeItems)
           "CHARACTERS" -> character.postValue(homeItems)
       }
    }
}