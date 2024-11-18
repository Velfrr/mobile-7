package com.lab7.data

import com.lab7.data.entity.AnimeCharactersResponse
import com.lab7.data.entity.AnimeDetailsResponse
import com.lab7.data.entity.AnimeSearchResponse
import com.lab7.data.entity.CharacterDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServerApi {
    @GET("/v4/anime")
    suspend fun getAnimeList(
        @Query("q") query: String? = null,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10
    ): AnimeSearchResponse


    @GET("/v4/anime/{id}")
    suspend fun getAnimeDetails(
        @Path("id") animeId: Int
    ): AnimeDetailsResponse

    @GET("/v4/anime/{id}/characters")
    suspend fun getAnimeCharacters(
        @Path("id") animeId: Int
    ): AnimeCharactersResponse

    @GET("/v4/characters/{id}")
    suspend fun getCharacterDetails(
        @Path("id") characterId: Int
    ): CharacterDetailsResponse

}
