package com.lab7.data.entity

data class AnimeSearchResponse(
    val data: List<AnimeData>,
)

data class AnimeDetailsResponse(
    val data: AnimeData
)

data class AnimeData(
    val mal_id: Int,
    val images: Images,
    val title: String,
    val score: Double?,
    val synopsis: String?,
)

