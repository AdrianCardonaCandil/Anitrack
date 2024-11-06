package com.example.anitrack

import android.app.Application
import com.example.anitrack.data.AppContainer
import com.example.anitrack.data.DefaultAppContainer

class AnitrackApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}