package com.example.codechallengedrop.di

import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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