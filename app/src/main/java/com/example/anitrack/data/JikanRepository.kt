package com.example.anitrack.data

import com.example.anitrack.model.Content
import com.example.anitrack.model.JikanCompoundResponse
import com.example.anitrack.network.JikanApiService

interface JikanRepository {
    suspend fun getCurrentSeason(
        sfw: Boolean = true,
        limit: Int = 0,
        page: Int = 1
    ):
            JikanCompoundResponse<Content>
}

class NetworkJikanRepository(private val jikanApiService: JikanApiService) : JikanRepository {
    override suspend fun getCurrentSeason(
        sfw: Boolean,
        limit: Int,
        page: Int
    ): JikanCompoundResponse<Content> = jikanApiService
        .getCurrentSeason(
            sfw = sfw,
            limit = limit,
            page = page
        )
}