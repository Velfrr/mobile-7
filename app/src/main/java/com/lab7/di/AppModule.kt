package com.lab7.di

import com.lab7.data.ServerApi
import com.lab7.data.cache.Cache
import com.lab7.ui.screens.animeCharacters.AnimeCharactersScreenViewModel
import com.lab7.ui.screens.animeDetails.AnimeDetailsScreenViewModel
import com.lab7.ui.screens.animeList.AnimeListScreenViewModel
import com.lab7.ui.screens.characterDetails.CharacterDetailsScreenViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.jikan.moe/v4/"

val appModule = module {
    single<ServerApi> {
        val client = OkHttpClient()
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val clientBuilder: OkHttpClient.Builder = client.newBuilder().addInterceptor(interceptor)

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(clientBuilder.build())
            .build()
            .create(ServerApi::class.java)
    }

    single { Cache(get()) }

    viewModel { AnimeListScreenViewModel(serverApi =  get(), cache = get()) }
    viewModel { AnimeDetailsScreenViewModel(get()) }
    viewModel { AnimeCharactersScreenViewModel(get()) }
    viewModel { CharacterDetailsScreenViewModel(get()) }
}
