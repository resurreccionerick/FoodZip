package com.example.foodzip.domain

interface FoodRepository {
    suspend fun getRandomMeal()
}