package com.example.codechallengedrop.data.repository

import com.example.codechallengedrop.data.response.Beer
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BeerRepository {

    @GET("beers/")
    suspend fun getBeerList(@Query("page") number: Int): List<Beer>

    @GET("beers/{id}")
    suspend fun getBeerDetail(@Path("id") id: Int): List<Beer>

}
