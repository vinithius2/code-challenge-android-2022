package com.example.codechallengedrop.di

import com.example.codechallengedrop.data.repository.BeerRepository
import com.example.codechallengedrop.data.repository.BeerRepositoryData
import com.example.codechallengedrop.ui.BeerViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val repositoryModule = module {
    single { get<Retrofit>().create(BeerRepository::class.java) }
}

val repositoryDataModule = module {
    single { BeerRepositoryData(get()) }
}

val viewModelModule = module {
    viewModel { BeerViewModel(get()) }
}

val remoteDataModule = module {
    single { retrofit() }
}

private fun retrofit(): Retrofit {

    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        .build()

    return Retrofit.Builder()
        .baseUrl("https://api.punkapi.com/v2/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}
