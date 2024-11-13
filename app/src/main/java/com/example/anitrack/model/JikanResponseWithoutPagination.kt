package com.example.anitrack.model

import kotlinx.serialization.Serializable

@Serializable
data class JikanResponseWithoutPagination<T>(
    val data: T? = null
)