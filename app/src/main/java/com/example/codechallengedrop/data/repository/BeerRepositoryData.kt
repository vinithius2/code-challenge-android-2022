package com.example.codechallengedrop.data.repository

import com.example.codechallengedrop.data.response.Beer
import retrofit2.Response

class BeerRepositoryData(
    private val repository: BeerRepository
) {

    suspend fun beerList(number: Int = 1): List<Beer> {
        return repository.getBeerList(number)
    }

}
