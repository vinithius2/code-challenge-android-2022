package com.example.codechallengedrop.data.repository

import com.example.codechallengedrop.data.response.Beer
import retrofit2.http.GET
import retrofit2.http.Query

interface BeerRepository {

    @GET("/beers/?page={number}&per_page=100")
    suspend fun getBeerList(@Query("number") number: Int): List<Beer>

    @GET("/beers/{id}")
    suspend fun getBeerDetail(@Query("id") id: Int): Beer

}
