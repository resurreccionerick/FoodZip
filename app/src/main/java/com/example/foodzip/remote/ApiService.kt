package com.example.foodzip.remote

import com.example.pagkain_mvvm.models.random.FoodListResponse
import retrofit2.http.GET

interface ApiService {

    @GET("random.php")
    suspend fun getRandomMeal(): FoodListResponse

}