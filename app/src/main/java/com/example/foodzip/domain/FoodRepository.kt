package com.example.foodzip.domain

import com.example.foodzip.models.ResultAddedFavorites
import com.example.foodzip.models.ResultCategoryInfoList
import com.example.foodzip.models.ResultCategoryList
import com.example.foodzip.models.ResultFavorites
import com.example.foodzip.models.ResultPopularList
import com.example.foodzip.models.ResultSearch
import com.example.foodzip.models.ResultType
import com.example.pagkain_mvvm.models.random.MealsItem
import kotlinx.coroutines.flow.Flow

interface FoodRepository {
    suspend fun getRandomMeal(): ResultType
    suspend fun getMealInfo(id: String): ResultType
    suspend fun getMealListPopular(): ResultPopularList
    suspend fun getCategories(): ResultCategoryList
    suspend fun getCategoriesFood(id: String): ResultCategoryInfoList

    //room
    suspend fun insertFavorite(fav: MealsItem): ResultFavorites
    suspend fun deleteFavorite(fav: MealsItem): ResultFavorites
    suspend fun getFavorites(): ResultAddedFavorites
    suspend fun searchMeal(meal: String): ResultSearch
}