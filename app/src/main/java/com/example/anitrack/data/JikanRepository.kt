package com.example.anitrack.data

import com.example.anitrack.model.Character
import com.example.anitrack.model.Content
import com.example.anitrack.model.JikanResponseWithPagination
import com.example.anitrack.model.JikanResponseWithoutPagination
import com.example.anitrack.network.JikanApiService

interface JikanRepository {
    suspend fun getSeason (
        upcoming: Boolean = false,
        sfw: Boolean? = true,
        limit: Int? = null,
        page: Int? = 1
    ): JikanResponseWithPagination<Content>

    suspend fun getTopAnime (
        sfw: Boolean? = true,
        limit: Int? = null,
        page: Int? = 1
    ): JikanResponseWithPagination<Content>

    suspend fun getAnimeById (
        id: Int
    ): JikanResponseWithoutPagination<Content>

    suspend fun getAnimeCharacters (
        id: Int
    ): JikanResponseWithoutPagination<List<Character>?>
}

class NetworkJikanRepository(private val jikanApiService: JikanApiService) : JikanRepository {
    override suspend fun getSeason (
        upcoming: Boolean,
        sfw: Boolean?,
        limit: Int?,
        page: Int?
    ): JikanResponseWithPagination<Content> =
        if (upcoming) jikanApiService.getUpcomingSeason(
            sfw = sfw,
            limit = limit,
            page = page
        )
        else jikanApiService.getCurrentSeason(
            sfw = sfw,
            limit = limit,
            page = page
        )

    override suspend fun getTopAnime(
        sfw: Boolean?,
        limit: Int?,
        page: Int?
    ): JikanResponseWithPagination<Content> = jikanApiService.getTopAnime(
        sfw = sfw,
        limit = limit,
        page = page
    )

    override suspend fun getAnimeById(id: Int): JikanResponseWithoutPagination<Content> =
        jikanApiService.getAnimeById(
            id = id
        )

    override suspend fun getAnimeCharacters(id: Int): JikanResponseWithoutPagination<List<Character>?> =
        jikanApiService.getAnimeCharacters(
            id = id
        )

}