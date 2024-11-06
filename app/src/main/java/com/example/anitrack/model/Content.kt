package com.example.anitrack.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed class Content {
    // Jikan Serializable Content Implementation For Retrofit Service
    @Serializable
    class JikanContent(
        @SerialName("mal_id")
        val id: Int,
        val images: Images?,
        val trailer: Trailer?,
        val title: String?,
        @SerialName("title_english")
        val englishTitle: String?,
        @SerialName("title_japanese")
        val japaneseTitle: String?,
        val type: String?,
        val source: String?,
        val episodes: Int?,
        val status: String?,
        val aired: Aired?,
        val duration: String?,
        val rating: String?,
        val score: Float?,
        val synopsis: String?,
        val season: String?,
        val year: Int?,
        val studios: List<Studio>?,
        val genres: List<Genre>?
    ) : Content() {
        @Serializable
        data class Images(
            val jpg: Jpg?,
            val webp: Webp?
        ) {
            @Serializable
            data class Jpg (
                @SerialName("large_image_url")
                val coverImage: String?
            )
            @Serializable
            data class Webp (
                @SerialName("large_image_url")
                val coverImage: String?
            )
        }
        @Serializable
        data class Trailer(
            val images: TrailerImages?
        ) {
            @Serializable
            data class TrailerImages(
                @SerialName("maximum_image_url")
                val backgroundImage: String?
            )
        }
        @Serializable
        data class Aired(
            val from: String?,
            val to: String?,
            val prop: AiredProp?
        ) {
            @Serializable
            data class AiredProp(
                val from: Prop?,
                val to: Prop?
            ) {
                @Serializable
                data class Prop(
                    val day: Int?,
                    val month: Int?,
                    val year: Int?
                )
            }
        }
        @Serializable
        data class Studio(
            @SerialName("mal_id")
            val id: Int?,
            val type: String?,
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
}

