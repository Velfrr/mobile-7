package com.lab7.data.cache

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.lab7.data.entity.AnimeSearchResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("cache")

class Cache(private val context: Context) {
    private val gson = Gson()

    companion object {
        private val ANIME_SEARCH_RESPONSE_KEY = stringPreferencesKey("anime_list")
    }

    val cachedAnimeSearchResponse: Flow<AnimeSearchResponse?> = context.dataStore.data
        .map { preferences ->
            val json = preferences[ANIME_SEARCH_RESPONSE_KEY]
            json?.let {
                gson.fromJson(it, AnimeSearchResponse::class.java)
            }
        }

    suspend fun saveAnimeSearchResponse(response: AnimeSearchResponse) {
        val json = gson.toJson(response)
        context.dataStore.edit { preferences ->
            preferences[ANIME_SEARCH_RESPONSE_KEY] = json
        }
    }
}
