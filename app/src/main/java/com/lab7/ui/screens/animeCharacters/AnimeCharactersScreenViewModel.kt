package com.lab7.ui.screens.animeCharacters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lab7.data.ServerApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log
import com.lab7.data.entity.AnimeCharactersResponse

class AnimeCharactersScreenViewModel(
    private val serverApi: ServerApi
) : ViewModel() {

    private val _animeCharactersStateFlow = MutableStateFlow<AnimeCharactersResponse?>(null)
    val animeCharactersStateFlow: StateFlow<AnimeCharactersResponse?>
        get() = _animeCharactersStateFlow

    fun fetchAnimeCharacters(animeId: Int) {
        viewModelScope.launch {
            try {
                val animeCharactersResponse = serverApi.getAnimeCharacters(animeId = animeId)
                _animeCharactersStateFlow.value = animeCharactersResponse
            } catch (e: Exception) {
                Log.e("AnimeCharacters", "Failed to load anime characters for anime with ID $animeId", e)
            }
        }
    }
}
