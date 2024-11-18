package com.lab7.ui.screens.animeCharacters

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.getViewModel
import androidx.compose.runtime.getValue
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

@Composable
fun AnimeCharactersScreen(
    animeId: Int,
    viewModel: AnimeCharactersScreenViewModel = getViewModel(),
    onCharacterSelected: (Int) -> Unit
) {
    val charactersListResponse by viewModel.animeCharactersStateFlow.collectAsState(initial = null)
    val charactersList = charactersListResponse?.data.orEmpty()

    LaunchedEffect(animeId) {
        viewModel.fetchAnimeCharacters(animeId)
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(charactersList) { characterRole ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clickable { onCharacterSelected(characterRole.character.mal_id) },
                colors = CardDefaults.cardColors(
                    containerColor = androidx.compose.material3.MaterialTheme.colorScheme.tertiaryContainer
                ),
                elevation = CardDefaults.cardElevation(4.dp),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                ) {
                    Column (modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)) {
                        Image(
                            painter = rememberAsyncImagePainter(characterRole.character.images.jpg.image_url),
                            contentDescription = "Character Image",
                            modifier = Modifier.size(120.dp).align(Alignment.CenterHorizontally)
                        )
                    }
                    Text(
                        text = characterRole.character.name,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Text(
                        text = "Role: ${characterRole.role}",
                        style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}
