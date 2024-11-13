package com.example.anitrack.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Character(
    val character: CharacterData? = null
) {
    @Serializable
    data class CharacterData(
        @SerialName("mal_id")
        val id: Int = 0,
        val name: String? = null,
        private val images: Images? = null,
        val image: String? = images?.webp?.imageUrl ?: images?.jpg?.imageUrl,
    ) {
        @Serializable
        data class Images(
            val jpg: Jpg? = null,
            val webp: Webp? = null,
        ) {
            @Serializable
            data class Jpg(
                @SerialName("image_url")
                val imageUrl: String? = null,
            )
            @Serializable
            data class Webp(
                @SerialName("image_url")
                val imageUrl: String? = null,
            )
        }
    }
}

data class CharacterList(
    val data: List<Character>? = null
)
