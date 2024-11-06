package com.example.marvelmovieapp.network.retrofit

import com.example.marvelmovieapp.network.service.MovieService
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

object Retrofit {

    private val movieURL = "https://gateway.marvel.com/v1/public/"

    fun getMovieService() : MovieService {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(movieURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofitBuilder.create(MovieService::class.java)
    }

}