package com.example.anitrack.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JikanResponseWithPagination<T> (
    val data: List<T>? = null,
    val pagination: Pagination? = null
) {
    @Serializable
    data class Pagination(
        @SerialName("last_visible_page")
        val lastVisiblePage: Int? = null,
        @SerialName("has_next_page")
        val hasNextPage: Boolean? = null,
        @SerialName("current_page")
        val currentPage: Int? = null,
        val items: Items? = null
    ) {
        @Serializable
        data class Items(
            val count: Int? = null,
            val total: Int? = null,
            @SerialName("per_page")
            val perPage: Int? = null
        )
    }
}