package com.lab7.ui.screens.animeDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lab7.data.ServerApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log
import com.lab7.data.entity.AnimeDetailsResponse

class AnimeDetailsScreenViewModel(
    private val serverApi: ServerApi
) : ViewModel() {

    private val _animeDetailsStateFlow = MutableStateFlow<AnimeDetailsResponse?>(null)
    val animeDetailsStateFlow: StateFlow<AnimeDetailsResponse?>
        get() = _animeDetailsStateFlow

    fun fetchAnimeDetails(animeId: Int) {
        viewModelScope.launch {
            try {
                val animeDetailsResponse = serverApi.getAnimeDetails(animeId = animeId)
                _animeDetailsStateFlow.value = animeDetailsResponse
            } catch (e: Exception) {
                Log.e("AnimeDetails", "Failed to load anime details for ID $animeId", e)
            }
        }
    }
}
