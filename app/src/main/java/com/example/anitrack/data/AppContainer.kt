package com.example.anitrack.data

import com.example.anitrack.network.JikanApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val jikanRepository: JikanRepository
    val databaseRepository: DatabaseRepository
    val authRepository: AuthRepository
    val storageRepository: StorageRepository
}

class DefaultAppContainer : AppContainer {
    // BaseUrl for Retrofit service
    private val jikanBaseUrl: String = "https://api.jikan.moe/v4/"

    /* Json instance for the retrofit converter factory. Assigning the possibility to
     * ignore unknown keys in the JSON responses
     * */
    private val json = Json { ignoreUnknownKeys = true }

    // Retrofit object instantiation
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
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

    // Initializing the firebase container with the firebase services
    private val firebaseContainer: FirebaseContainer = DefaultFirebaseContainer()

    // Initializing the FirestoreFirebaseRepository
    override val databaseRepository: DatabaseRepository by lazy {
        FirestoreFirebaseRepository(firebaseContainer.firestoreService)
    }

    // Initializing the AuthFirebaseRepository
    override val authRepository: AuthRepository by lazy {
        AuthFirebaseRepository(firebaseContainer.authService)
    }

    // Initializing the StorageRepository
    override val storageRepository: StorageRepository by lazy {
        firebaseContainer.storageRepository
    }

}

