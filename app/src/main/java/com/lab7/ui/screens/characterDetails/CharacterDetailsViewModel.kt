package com.lab7.ui.screens.characterDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lab7.data.ServerApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log
import com.lab7.data.entity.CharacterDetailsResponse

class CharacterDetailsScreenViewModel(
    private val serverApi: ServerApi
) : ViewModel() {
    private val _characterDetailsStateFlow = MutableStateFlow<CharacterDetailsResponse?>(null)
    val characterDetailsStateFlow: StateFlow<CharacterDetailsResponse?>
        get() = _characterDetailsStateFlow

    fun fetchCharacterDetails(characterId: Int) {
        viewModelScope.launch {
            try {
                val characterDetailsResponse = serverApi.getCharacterDetails(characterId = characterId)
                _characterDetailsStateFlow.value = characterDetailsResponse
                Log.d("CharacterDetailsVM", "Character details for ID $characterId loaded successfully")
            } catch (e: Exception) {
                Log.e("CharacterDetailsVM", "Failed to load character details for ID $characterId", e)
            }
        }
    }
}
