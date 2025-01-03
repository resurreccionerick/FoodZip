package com.example.foodzip.remote

import retrofit2.http.GET

interface ApiService {

    @GET("")
    suspend fun getRandomMeal()

}