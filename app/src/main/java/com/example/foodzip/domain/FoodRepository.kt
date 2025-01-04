package com.example.foodzip.domain

import com.example.foodzip.models.ResultCategoryInfoList
import com.example.foodzip.models.ResultCategoryList
import com.example.foodzip.models.ResultPopularList
import com.example.foodzip.models.ResultType

interface FoodRepository {
    suspend fun getRandomMeal(): ResultType
    suspend fun getMealInfo(id: String): ResultType
    suspend fun getMealListPopular(): ResultPopularList
    suspend fun getCategories(): ResultCategoryList
    suspend fun getCategoriesFood(id: String): ResultCategoryInfoList
}