package com.lab7.data.entity

data class AnimeCharactersResponse(
    val data: List<CharacterRole>
)

data class CharacterDetailsResponse(
    val data: CharacterDetails
)

data class CharacterRole(
    val character: Character,
    val role: String,
)

data class Character(
    val mal_id: Int,
    val url: String,
    val images: Images,
    val name: String
)

data class CharacterDetails(
    val mal_id: Int,
    val images: Images,
    val name: String,
    val nicknames: List<String>,
    val favorites: Int,
    val about: String?
)
