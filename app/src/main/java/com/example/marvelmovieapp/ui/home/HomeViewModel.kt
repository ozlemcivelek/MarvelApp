package com.example.marvelmovieapp.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.marvelmovieapp.base.BaseViewModel
import com.example.marvelmovieapp.models.MyComics
import com.example.marvelmovieapp.models.MyEvents
import com.example.marvelmovieapp.models.SliderModel
import com.example.marvelmovieapp.network.retrofit.Retrofit

class HomeViewModel : BaseViewModel() {

    private var service = Retrofit.getMovieService()
    val imageSlider: MutableLiveData<List<SliderModel>> = MutableLiveData(emptyList())
    val events: MutableLiveData<List<MyEvents>> = MutableLiveData(emptyList())

    init {
        getComics()
        getEvents()
    }

    private fun getComics() {
        sendRequest(call = {
            service.getComics()
        }, result = { result ->

            val safeResult = result.data.results
            safeResult.let { it ->
                it.forEach {
                    val item = SliderModel(
                        id = it.id,
                        imageUrl = it.thumbnail.path + "/" + "detail" + "." + it.thumbnail.extension,
                        imageTitle = it.title,
                    )
                    imageSlider.value = listOf(item)
                }
            }
            //comicResponse.value = safeResult
            Log.d("TAG", "getComics: $safeResult")
        })
    }

    private fun getEvents(){
        sendRequest(call = {
            service.getEvents()
        }, result = { result ->

            val safeResult = result.data.results
            safeResult.let { it ->
                it.forEach {
                    val item = MyEvents(
                        id = it.id,
                        imageUrl = it.thumbnail.path + "/" + "portrait_medium" + "." + it.thumbnail.extension,
                        imageTitle = it.title,
                        description = it.description
                    )
                    events.value = listOf(item)
                }
            }

        })

    }
}