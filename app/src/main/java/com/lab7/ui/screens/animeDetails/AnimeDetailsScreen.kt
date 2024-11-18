package com.lab7.ui.screens.animeDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.getViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

@Composable
fun AnimeDetailsScreen(
    animeId: Int,
    viewModel: AnimeDetailsScreenViewModel = getViewModel(),
    onViewCharacters: (Int) -> Unit
) {
    val animeDetailsResponse by viewModel.animeDetailsStateFlow.collectAsState()
    val animeDetails = animeDetailsResponse?.data;

    LaunchedEffect(animeId) {
        viewModel.fetchAnimeDetails(animeId)
    }

    animeDetails?.let { anime ->
        val scrollState = rememberScrollState()

        Column(modifier = Modifier.fillMaxSize().verticalScroll(scrollState)) {
            Image(
                painter = rememberAsyncImagePainter(anime.images.jpg.large_image_url),
                contentDescription = "Anime image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(520.dp)
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = anime.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Score: ${anime.score}",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = anime.synopsis.orEmpty(),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Button(
                    onClick = { onViewCharacters(anime.mal_id) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("View Characters")
                }
            }
        }
    }
}
