package com.example.anitrack.data

import retrofit2.Retrofit

interface AppContainer {
    val jikanRepository: JikanRepository
}

class DefaultAppContainer : AppContainer {
    // BaseUrl for Retrofit service
    private val jikanBaseUrl: String = "https://api.jikan.moe/v4"
    override val jikanRepository = JikanRepository()
}

