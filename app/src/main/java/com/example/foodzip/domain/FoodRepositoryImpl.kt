package com.example.foodzip.domain

import android.app.Application
import com.example.foodzip.remote.ApiService

class FoodRepositoryImpl(private val api: ApiService, private val context: Application) :
    FoodRepository {
    override suspend fun getRandomMeal() {

    }
}