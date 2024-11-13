package com.example.marvelmovieapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MarvelApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Diğer başlatma işlemleri burada yapılabilir
    }
}
