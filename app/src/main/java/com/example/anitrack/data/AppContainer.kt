package com.example.anitrack.data

import com.example.anitrack.network.JikanApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val jikanRepository: JikanRepository
}

class DefaultAppContainer : AppContainer {
    // BaseUrl for Retrofit service
    private val jikanBaseUrl: String = "https://api.jikan.moe/v4"

    // Retrofit object instantiation
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(jikanBaseUrl)
        .build()

    // JikanApiService to retrofit injection
    private val jikanApiService: JikanApiService by lazy {
        retrofit.create(JikanApiService::class.java)
    }

    // Jikan repository object instantiation
    override val jikanRepository: JikanRepository by lazy {
        NetworkJikanRepository(jikanApiService)
    }
}

