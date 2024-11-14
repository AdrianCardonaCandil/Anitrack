package com.example.anitrack.model

import kotlinx.serialization.Serializable
import com.google.firebase.firestore.DocumentId

@Serializable
data class User(
    @DocumentId
    val id: String = "",
    val username: String = "",
    val email: String = "",
    val description: String = "",
    val profilePicture: String = "",

    val watching: List<String> = emptyList(),
    val completed: List<String> = emptyList(),
    val planToWatch: List<String> = emptyList(),
    val favorites: List<String> = emptyList(),

    val contentProgress: Map<String, Int> = emptyMap(),
    val createdAt: String? = null
)
