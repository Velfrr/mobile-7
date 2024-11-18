package com.lab7.ui.screens.characterDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.getViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

@Composable
fun CharacterDetailsScreen(
    characterId: Int,
    viewModel: CharacterDetailsScreenViewModel = getViewModel()
) {
    val characterDetailsResponse by viewModel.characterDetailsStateFlow.collectAsState()
    val characterDetails = characterDetailsResponse?.data

    LaunchedEffect(characterId) {
        viewModel.fetchCharacterDetails(characterId)
    }

    characterDetails?.let { character ->
        val scrollState = rememberScrollState()

        Column(modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(scrollState)) {
            Image(
                painter = rememberAsyncImagePainter(character.images.jpg.image_url),
                contentDescription = "Character Image",
                modifier = Modifier
                    .aspectRatio(1f)
                    .padding(bottom = 16.dp)
            )

            Text(
                text = character.name,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Favorites: ${character.favorites}",
                style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            if (character.nicknames.isNotEmpty()) {
                Text(
                    text = "Nicknames:",
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                character.nicknames.forEach { nickname ->
                    Text(
                        text = nickname,
                        style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 2.dp)
                    )
                }
            }

            Text(
                text = character.about.orEmpty(),
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}