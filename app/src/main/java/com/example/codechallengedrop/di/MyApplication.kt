package com.example.codechallengedrop.di

import android.app.Application
import com.example.codechallengedrop.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication: Application() {

    private val appModules by lazy {
        listOf(
            repositoryModule,
            repositoryDataModule,
            remoteDataModule
        )
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@MyApplication)
            modules(appModules)
        }
    }

}