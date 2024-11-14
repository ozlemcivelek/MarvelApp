package com.example.marvelmovieapp.ui.home

import androidx.lifecycle.MutableLiveData
import com.example.marvelmovieapp.base.BaseViewModel
import com.example.marvelmovieapp.models.HomeItem
import com.example.marvelmovieapp.models.SliderModel
import com.example.marvelmovieapp.network.retrofit.Retrofit

class HomeViewModel : BaseViewModel() {

    private var service = Retrofit.getMovieService()
    val imageSlider: MutableLiveData<List<SliderModel>> = MutableLiveData(emptyList())
    val events: MutableLiveData<List<HomeItem>> = MutableLiveData(emptyList())
    val characters: MutableLiveData<List<HomeItem>> = MutableLiveData(emptyList())
    val creators: MutableLiveData<List<HomeItem>> = MutableLiveData(emptyList())
    val comics: MutableLiveData<List<HomeItem>> = MutableLiveData(emptyList())

    init {
        getSeries()
        getEvents()
        getCharacters()
        getCreators()
        getComics()
    }

    private fun getSeries() {
        sendRequest(call = {
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
        })
    }

    private fun getComics() {
        sendRequest(call = {
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
        })
    }

    private fun getEvents() {
        sendRequest(call = {
            service.getEvents()
        }, result = { result ->

            val safeResult = result.data.results
            safeResult.let { it ->
                val items = it.map {
                    HomeItem(
                        id = it.id,
                        type = "EVENTS",
                        imageUrl = it.thumbnail.path + "/" + "portrait_medium" + "." + it.thumbnail.extension,
                        imageTitle = it.title,
                        description = it.description
                    )
                }
                events.value = items
            }

        })

    }

    private fun getCharacters() {
        sendRequest(call = {
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
        })
    }

    private fun getCreators() {
        sendRequest(call = {
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
        })
    }
}
