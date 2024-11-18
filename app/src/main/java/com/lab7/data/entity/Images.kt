package com.lab7.data.entity

data class Images(
    val jpg: ImageDetails,
    val webp: ImageDetails
)

data class ImageDetails(
    val image_url: String,
    val small_image_url: String,
    val large_image_url: String
)