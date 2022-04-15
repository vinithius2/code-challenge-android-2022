package com.example.codechallengedrop.di

import com.example.codechallengedrop.data.repository.BeerRepository
import com.example.codechallengedrop.data.repository.BeerRepositoryData
import com.example.codechallengedrop.ui.BeerViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val repositoryModule = module {
    single { get<Retrofit>().create(BeerRepository::class.java) }
}

val repositoryDataModule = module {
    single { BeerRepositoryData(get()) }
}

val viewModelModule = module {
    viewModel { BeerViewModel(get()) }
}

/******************** REMOTE ********************/

val remoteDataModule = module {
    single { retrofit() }
}

private fun retrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://api.punkapi.com/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}