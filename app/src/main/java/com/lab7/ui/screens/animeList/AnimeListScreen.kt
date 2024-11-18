package com.lab7.ui.screens.animeList

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.getViewModel
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

@Composable
fun AnimeListScreen(
    viewModel: AnimeListScreenViewModel = getViewModel(),
    onAnimeSelected: (Int) -> Unit
) {
    val animeListResponse by viewModel.animeListStateFlow.collectAsState()
    val animeList = animeListResponse?.data.orEmpty()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Anime List",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp),
            fontSize = 32.sp
        )

        if (animeListResponse == null) {
            Text(
                text = "Loading...",
                modifier = Modifier.fillMaxSize(),
                fontSize = 20.sp
            )
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(animeList) { anime ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable { onAnimeSelected(anime.mal_id) },
                        colors = CardDefaults.cardColors(
                            containerColor = androidx.compose.material3.MaterialTheme.colorScheme.primaryContainer
                        ),
                        elevation = CardDefaults.cardElevation(6.dp),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                    ) {
                        Row(modifier = Modifier.padding(horizontal = 5.dp, vertical = 12.dp)) {
                            Image(
                                painter = rememberAsyncImagePainter(anime.images.jpg.image_url),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(end = 8.dp)
                            )
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = anime.title,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )
                                Text(
                                    text = anime.synopsis.orEmpty(),
                                    maxLines = 3,
                                    overflow = TextOverflow.Ellipsis,
                                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
