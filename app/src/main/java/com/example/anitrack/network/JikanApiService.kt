package com.example.anitrack.network

import com.example.anitrack.model.Character
import com.example.anitrack.model.Content
import com.example.anitrack.model.JikanResponseWithPagination
import com.example.anitrack.model.JikanResponseWithoutPagination
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JikanApiService {
    @GET("seasons/now")
    suspend fun getCurrentSeason(
        @Query("sfw") sfw: Boolean? = true,
        @Query("limit") limit: Int? = null,
        @Query("page") page: Int? = 1
    ): JikanResponseWithPagination<Content>

    @GET("seasons/upcoming")
    suspend fun getUpcomingSeason(
        @Query("sfw") sfw: Boolean? = true,
        @Query("limit") limit: Int? = null,
        @Query("page") page: Int? = 1
    ): JikanResponseWithPagination<Content>

    @GET("top/anime")
    suspend fun getTopAnime(
        @Query("sfw") sfw: Boolean? = true,
        @Query("limit") limit: Int? = null,
        @Query("page") page: Int? = 1
    ): JikanResponseWithPagination<Content>

    @GET("anime/{id}")
    suspend fun getAnimeById(
        @Path("id") id: Int
    ): JikanResponseWithoutPagination<Content>

    @GET("anime/{id}/characters")
    suspend fun getAnimeCharacters(
        @Path("id") id: Int
    ): JikanResponseWithoutPagination<List<Character>?>

    @GET("anime")
    suspend fun animeSearch(
        @Query("sfw") sfw: Boolean? = true,
        @Query("limit") limit: Int? = null,
        @Query("page") page: Int? = 1,
        @Query("q") q: String
    ): JikanResponseWithPagination<Content>
}