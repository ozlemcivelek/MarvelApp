package com.example.marvelmovieapp.ui.home

import androidx.lifecycle.MutableLiveData
import com.example.marvelmovieapp.base.BaseViewModel
import com.example.marvelmovieapp.models.HomeItem
import com.example.marvelmovieapp.models.LoadingState
import com.example.marvelmovieapp.models.SliderModel
import com.example.marvelmovieapp.network.retrofit.Retrofit

class HomeViewModel : BaseViewModel() {

    private var service = Retrofit.getMovieService()
    val imageSlider: MutableLiveData<List<SliderModel>> = MutableLiveData(emptyList())
    val events: MutableLiveData<List<HomeItem>> = MutableLiveData(emptyList())
    val characters: MutableLiveData<List<HomeItem>> = MutableLiveData(emptyList())
    val creators: MutableLiveData<List<HomeItem>> = MutableLiveData(emptyList())
    val comics: MutableLiveData<List<HomeItem>> = MutableLiveData(emptyList())

    val sliderLoadingState = MutableLiveData<LoadingState>()
    val eventLoadingState = MutableLiveData<LoadingState>()
    val comicLoadingState = MutableLiveData<LoadingState>()
    val characterLoadingState = MutableLiveData<LoadingState>()
    val creatorLoadingState = MutableLiveData<LoadingState>()

    init {
        getSeries()
        getEvents()
        getCharacters()
        getCreators()
        getComics()
    }

    private fun getSeries() {
        sendRequest(call = {
            sliderLoadingState.value = LoadingState.LOADING
            service.getSeries()
        }, result = { result ->

            val safeResult = result.data.results
            safeResult.let { it ->
                val items = it.map {
                    SliderModel(
                        id = it.id,
                        imageUrl = it.thumbnail.path + "/" + "detail" + "." + it.thumbnail.extension,
                        imageTitle = it.title,
                    )
                }
                imageSlider.value = items
            }
            sliderLoadingState.value = LoadingState.SUCCESS
        })
    }

    private fun getComics() {
        sendRequest(call = {
            comicLoadingState.value = LoadingState.LOADING
            service.getComics()
        }, result = { result ->

            val safeResult = result.data.results
            safeResult.let { it ->
                val items = it.map {
                    HomeItem(
                        id = it.id,
                        type = "COMICS",
                        imageUrl = it.thumbnail.path + "/" + "portrait_medium" + "." + it.thumbnail.extension,
                        imageTitle = it.title,
                        description = "it.description"
                    )
                }
                comics.value = items
            }
            comicLoadingState.value = LoadingState.SUCCESS
        })
    }

    private fun getEvents() {
        sendRequest(call = {
            eventLoadingState.value = LoadingState.LOADING
            service.getEvents()
        }, result = { result ->

            val safeResult = result.data.results
            safeResult.let { it ->
                val items = it.map {
                    val path = if (it.thumbnail.path.startsWith("http:")) it.thumbnail.path.replace(
                        "http:",
                        "https:"
                    ) else it.thumbnail.path
                    HomeItem(
                        id = it.id,
                        type = "EVENTS",
                        imageUrl = path + "/" + "portrait_medium" + "." + it.thumbnail.extension,
                        imageTitle = it.title,
                        description = it.description
                    )
                }
                events.value = items
            }
            eventLoadingState.value = LoadingState.SUCCESS
        })

    }

    private fun getCharacters() {
        sendRequest(call = {
            characterLoadingState.value = LoadingState.LOADING
            service.getCharacters()
        }, result = { result ->

            val safeResult = result.data.results
            safeResult.let { it ->
                val items = it.map {
                    HomeItem(
                        id = it.id,
                        type = "CHARACTERS",
                        imageUrl = it.thumbnail.path + "/" + "portrait_medium" + "." + it.thumbnail.extension,
                        imageTitle = it.name,
                        description = it.description

                    )
                }
                characters.value = items
            }
            characterLoadingState.value = LoadingState.SUCCESS
        })
    }

    private fun getCreators() {
        sendRequest(call = {
            creatorLoadingState.value = LoadingState.LOADING
            service.getCreators()
        }, result = { result ->

            val safeResult = result.data.results
            safeResult.let { it ->
                val items = it.map {
                    HomeItem(
                        id = it.id,
                        type = "CREATORS",
                        imageUrl = it.thumbnail.path + "/" + "portrait_medium" + "." + it.thumbnail.extension,
                        imageTitle = it.firstName,
                        description = it.fullName
                    )
                }
                creators.value = items
            }
            creatorLoadingState.value = LoadingState.SUCCESS
        })
    }
}
