package com.example.marvelmovieapp.base

import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class BaseViewModel : ViewModel() {
    fun <T> sendRequest(call: () -> Call<T>, result: (T) -> Unit) {
        call.invoke().enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    result.invoke(response.body()!!)
                } else {
                    handleException(Exception(response.errorBody().toString()))
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                handleException(t)
            }
        })

    }

    fun handleException(t: Throwable) {
        t.printStackTrace()

    }
}