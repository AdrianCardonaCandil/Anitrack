package com.example.anitrack.network

import com.example.anitrack.model.Content
import com.example.anitrack.model.JikanCompoundResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface JikanApiService {
    @GET("seasons/now")
    suspend fun getCurrentSeason(
        @Query("sfw") sfw: Boolean = true,
        @Query("limit") limit: Int = 0,
        @Query("page") page: Int = 1
    ): JikanCompoundResponse<Content>
}