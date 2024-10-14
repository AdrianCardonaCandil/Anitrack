package com.example.courses.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Topic(
    @StringRes
    val nameResourceId: Int,
    val totalCourses: Int,
    @DrawableRes
    val imageResourceId: Int
)
