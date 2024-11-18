package com.example.marvelmovieapp.network.service

import com.example.marvelmovieapp.BuildConfig
import com.example.marvelmovieapp.network.model.CharactersResponse
import com.example.marvelmovieapp.network.model.ComicResponse
import com.example.marvelmovieapp.network.model.CreatorsResponse
import com.example.marvelmovieapp.network.model.EventsResponse
import com.example.marvelmovieapp.network.model.MarvelResponse
import com.example.marvelmovieapp.network.model.SeriesResponse
import retrofit2.Call
import retrofit2.http.GET

interface MovieService {
    @GET("comics?$HEADER_VALUES")
    fun getComics(): Call<MarvelResponse<ComicResponse>>

    @GET("series?$HEADER_VALUES")
    fun getSeries(): Call<MarvelResponse<SeriesResponse>>

    @GET("events?$HEADER_VALUES")
    fun getEvents(): Call<MarvelResponse<EventsResponse>>

    @GET("characters?$HEADER_VALUES")
    fun getCharacters(): Call<MarvelResponse<CharactersResponse>>

    @GET("creators?$HEADER_VALUES")
    fun getCreators(): Call<MarvelResponse<CreatorsResponse>>

    companion object {
        private const val HEADER_VALUES =
            "ts=1&apikey=${BuildConfig.API_KEY}&hash=${BuildConfig.HASH}"
    }

}