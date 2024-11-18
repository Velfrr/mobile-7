package com.lab7.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lab7.ui.screens.animeCharacters.AnimeCharactersScreen
import com.lab7.ui.screens.animeDetails.AnimeDetailsScreen
import com.lab7.ui.screens.animeList.AnimeListScreen
import com.lab7.ui.screens.characterDetails.CharacterDetailsScreen

const val SCREEN_ANIME_LIST = "animeList"
const val SCREEN_ANIME_DETAILS = "animeDetails"
const val SCREEN_ANIME_CHARACTERS = "animeCharacters"
const val SCREEN_CHARACTER_DETAILS = "characterDetails"

@Composable
fun NavigationGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SCREEN_ANIME_LIST
    ) {
        composable(SCREEN_ANIME_LIST) {
            AnimeListScreen {
                animeId ->
                navController.navigate("$SCREEN_ANIME_DETAILS/$animeId")
            }
        }

        composable(
            route = "$SCREEN_ANIME_DETAILS/{animeId}",
            arguments = listOf(navArgument("animeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val animeId = backStackEntry.arguments?.getInt("animeId") ?: return@composable

            AnimeDetailsScreen(
                animeId = animeId,
                onViewCharacters = { navController.navigate("$SCREEN_ANIME_CHARACTERS/$animeId") }
            )
        }

        composable(
            route = "$SCREEN_ANIME_CHARACTERS/{animeId}",
            arguments = listOf(
                navArgument("animeId") { type = NavType.IntType },
            )
        ) { backStackEntry ->
            val animeId = backStackEntry.arguments?.getInt("animeId") ?: return@composable

            AnimeCharactersScreen(
                animeId = animeId,
                onCharacterSelected = { characterId ->
                    navController.navigate("$SCREEN_CHARACTER_DETAILS/$characterId")
                }
            )
        }

        composable(
            route = "$SCREEN_CHARACTER_DETAILS/{characterId}",
            arguments = listOf(navArgument("characterId") { type = NavType.IntType })
        ) { backStackEntry ->
            val characterId = backStackEntry.arguments?.getInt("characterId") ?: return@composable

            CharacterDetailsScreen(characterId = characterId)
        }
    }
}
