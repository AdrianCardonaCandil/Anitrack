package com.example.anitrack.model

import kotlinx.serialization.Serializable
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.Date

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

    val contentProgress: Map<String, Int> = emptyMap(),    // e.g., {"contentId": 3}

    @ServerTimestamp
    val createdAt: String = ""
)
