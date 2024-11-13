package com.example.marvelmovieapp.network.retrofit

import com.example.marvelmovieapp.network.service.MovieService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {

    private val movieURL = "https://gateway.marvel.com/v1/public/"

    fun getMovieService(): MovieService {

        val client = OkHttpClient.Builder()
            .build()
        return Retrofit.Builder()
            .baseUrl(movieURL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieService::class.java)
    }
}