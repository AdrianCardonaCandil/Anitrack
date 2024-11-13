package com.example.anitrack.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Character(
    val character: CharacterData?
) {
    @Serializable
    data class CharacterData(
        @SerialName("mal_id")
        val id: Int,
        val name: String?,
        private val images: Images?,
        val image: String? = images?.webp?.imageUrl ?: images?.jpg?.imageUrl,
    ) {
        @Serializable
        data class Images(
            val jpg: Jpg?,
            val webp: Webp?,
        ) {
            @Serializable
            data class Jpg(
                @SerialName("image_url")
                val imageUrl: String,
            )
            @Serializable
            data class Webp(
                @SerialName("image_url")
                val imageUrl: String,
            )
        }
    }
}

data class CharacterList(
    val data: List<Character>?
)
