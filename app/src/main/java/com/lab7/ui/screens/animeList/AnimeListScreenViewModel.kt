package com.lab7.ui.screens.animeList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lab7.data.ServerApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log
import com.lab7.data.cache.Cache
import com.lab7.data.entity.AnimeSearchResponse
import kotlinx.coroutines.flow.collectLatest

class AnimeListScreenViewModel(
    private val serverApi: ServerApi,
    private val cache: Cache
) : ViewModel() {

    private val _animeListStateFlow = MutableStateFlow<AnimeSearchResponse?>(null)
    val animeListStateFlow: StateFlow<AnimeSearchResponse?>
        get() = _animeListStateFlow

    init {
        fetchAnimeList()
    }

    private fun fetchAnimeList() {
        viewModelScope.launch {
            cache.cachedAnimeSearchResponse.collectLatest { cachedResponse ->
                if (cachedResponse != null) {
                    _animeListStateFlow.value = cachedResponse
                    Log.d("AnimeCache", "Cache loaded: ${cachedResponse.data.size} items")
                }

                try {
                    val response = serverApi.getAnimeList()
                    _animeListStateFlow.value = response
                    cache.saveAnimeSearchResponse(response)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
