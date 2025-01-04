package com.example.foodzip.remote

import com.example.pagkain_mvvm.models.category.Category
import com.example.pagkain_mvvm.models.category.categoryfood.CategoryFood
import com.example.pagkain_mvvm.models.popular.PopularList
import com.example.pagkain_mvvm.models.random.FoodListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("random.php")
    suspend fun getRandomMeal(): FoodListResponse

    @GET("filter.php?")
    suspend fun getPopularList(@Query("c") popularName: String): PopularList

    @GET("lookup.php?")
    suspend fun getMealDetails(@Query("i") id: String): FoodListResponse

    @GET("categories.php")
    suspend fun getCategories(): Category

    @GET("filter.php?")
    suspend fun getCategoriesFood(@Query("c") id: String): CategoryFood
}