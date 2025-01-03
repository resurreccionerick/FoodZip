package com.example.foodzip.domain

import android.app.Application
import com.example.foodzip.models.ResultType
import com.example.foodzip.remote.ApiService

class FoodRepositoryImpl(
    private val apiService: ApiService,
    private val context: Application
) : FoodRepository {
    
    override suspend fun getRandomMeal(): ResultType {
        return try {
            val response = apiService.getRandomMeal()
            if (response.meals.isNotEmpty()) {
                ResultType.Success(response.meals[0])
            } else {
                ResultType.Error("No meals found")
            }
        } catch (e: Exception) {
            ResultType.Error(e.localizedMessage ?: "Unknown error occurred")
        }
    }
}