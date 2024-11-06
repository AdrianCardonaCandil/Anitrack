package com.example.anitrack.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Content(
    @SerialName("mal_id")
    val id: Int,
    val title: String? = null,
    @SerialName("title_english")
    val englishTitle: String? = null,
    @SerialName("title_japanese")
    val japaneseTitle: String? = null,
    val type: String? = null,
    val source: String? = null,
    val episodes: Int? = null,
    val status: String? = null,
    val duration: String? = null,
    val rating: String? = null,
    val score: Float? = null,
    val synopsis: String? = null,
    val season: String? = null,
    val year: Int? = null,
    private val images: Images? = null,
    val coverImage: String? = images?.jpg?.largeImageUrl
        ?: images?.webp?.largeImageUrl
        ?: images?.jpg?.imageUrl
        ?: images?.webp?.imageUrl
        ?: images?.jpg?.smallImageUrl
        ?: images?.webp?.smallImageUrl,
    private val trailer: Trailer? = null,
    val backgroundImage: String? = trailer?.images?.maximumImageUrl
        ?: trailer?.images?.largeImageUrl
        ?: trailer?.images?.mediumImageUrl
        ?: trailer?.images?.imageUrl
        ?: trailer?.images?.smallImageUrl,
    private val aired: Aired? = null,
    val fromDate: String? = aired?.from,
    val toDate: String? = aired?.to,
    private val studios: List<Studio>? = null,
    val contentStudios: List<String?>? = studios?.map { it.name },
    private val genres: List<Genre>? = null,
    val contentGenres: List<String?>? = genres?.map { it.name }
) {
    @Serializable
    data class Images(
        val jpg: Jpg?,
        val webp: Webp?
    ) {
        @Serializable
        data class Jpg(
            @SerialName("small_image_url")
            val smallImageUrl: String?,
            @SerialName("image_url")
            val imageUrl: String?,
            @SerialName("large_image_url")
            val largeImageUrl: String?,
        )
        @Serializable
        data class Webp(
            @SerialName("small_image_url")
            val smallImageUrl: String?,
            @SerialName("image_url")
            val imageUrl: String?,
            @SerialName("large_image_url")
            val largeImageUrl: String?,
        )
    }
    @Serializable
    data class Trailer(
        val images: Images?
    ) {
        @Serializable
        data class Images(
            @SerialName("small_image_url")
            val smallImageUrl: String?,
            @SerialName("image_url")
            val imageUrl: String?,
            @SerialName("medium_image_url")
            val mediumImageUrl: String?,
            @SerialName("large_image_url")
            val largeImageUrl: String?,
            @SerialName("maximum_image_url")
            val maximumImageUrl: String?,
        )
    }
    @Serializable
    data class Aired(
        val from: String?,
        val to: String?
    )
    @Serializable
    data class Studio(
        @SerialName("mal_id")
        val id: Int?,
        val name: String?
    )
    @Serializable
    data class Genre(
        @SerialName("mal_id")
        val id: Int?,
        val type: String?,
        val name: String?
    )
}



