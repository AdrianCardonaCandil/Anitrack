package com.example.marsphotos.data

import com.example.marsphotos.network.MarsApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val marsPhotosRepository: MarsPhotosRepository
}

class DefaultAppContainer : AppContainer {

    /* We have created a container for the dependencies that the app needs. These dependencies
    * can be used throughout the app, so they must be in a common place that all the
    * activities can use.
    *
    * For example, the viewModel needs the dependency on a data repository to be able to.
    * get the photos of mars (marsPhotosRepository). To do this, the repository object is
    * created here along with all the repository along with all the sub dependencies that
    * this dependency needs, in this case, the case, the retrofit web service (retrofitService). */

    private val baseUrl =
        "https://android-kotlin-fun-mars-server.appspot.com"

    /**
     * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
     */
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    /* This web service was formerly exported to the application in a singleton object (marsApi)
     * which was accessed as follows: MarsApi.retrofitService.
     *
     * Now, this is the data source needed by the repository, so it is its dependency and is only
     * accessible from within it, and therefore dependency injection is used. */

    private val retrofitService: MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }

    override val marsPhotosRepository: MarsPhotosRepository by lazy {
        NetworkMarsPhotosRepository(retrofitService)
    }
}