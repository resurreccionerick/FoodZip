package com.example.foodzip.domain

import com.example.foodzip.database.FoodDAO
import com.example.foodzip.models.ResultAddedFavorites
import com.example.foodzip.models.ResultCategoryInfoList
import com.example.foodzip.models.ResultCategoryList
import com.example.foodzip.models.ResultFavorites
import com.example.foodzip.models.ResultPopularList
import com.example.foodzip.models.ResultType
import com.example.foodzip.remote.ApiService
import com.example.pagkain_mvvm.models.random.MealsItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject


class FoodRepositoryImpl @Inject constructor(
    private val foodDAO: FoodDAO,
    private val apiService: ApiService,
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

    override suspend fun getMealInfo(id: String): ResultType {
        return try {
            val response = apiService.getMealDetails(id)
            if (response.meals.isNotEmpty()) {
                ResultType.Success(response.meals[0])
            } else {
                ResultType.Error("No meals found")
            }
        } catch (e: Exception) {
            ResultType.Error(e.localizedMessage ?: "Unknown error occurred")
        }
    }

    override suspend fun getMealListPopular(): ResultPopularList {
        return try {
            val response = apiService.getPopularList("Dessert")

            if (response.popularMeals.isNotEmpty()) {
                ResultPopularList.Success(response.popularMeals)
            } else {
                ResultPopularList.Error("No Popular Meals found")
            }
        } catch (e: Exception) {
            ResultPopularList.Error(e.localizedMessage ?: "Unknown error occurred")
        }
    }

    override suspend fun getCategories(): ResultCategoryList {
        return try {
            val response = apiService.getCategories()

            if (response.categories.isNotEmpty()) {
                ResultCategoryList.Success(response.categories)
            } else {
                ResultCategoryList.Error("No Popular Meals found")
            }
        } catch (e: Exception) {
            ResultCategoryList.Error(e.localizedMessage ?: "Unknown error occurred")
        }
    }


    override suspend fun getCategoriesFood(id: String): ResultCategoryInfoList {
        return try {
            val response = apiService.getCategoriesFood(id)

            if (response.meals.isNotEmpty()) {
                ResultCategoryInfoList.Success(response.meals)
            } else {
                ResultCategoryInfoList.Error("No Popular Meals found")
            }

        } catch (e: Exception) {
            ResultCategoryInfoList.Error(e.localizedMessage ?: "Unknown error occurred")
        }
    }

    override suspend fun insertFavorite(fav: MealsItem): ResultFavorites {
        return try {
            foodDAO.upsertMeal(fav)
            ResultFavorites.Success("Inserted Successfully")
        } catch (e: Exception) {
            ResultFavorites.Error("Error: $e")
        }
    }

    override suspend fun deleteFavorite(fav: MealsItem): ResultFavorites {
        return try {
            foodDAO.deleteMeal(fav)
            ResultFavorites.Success("Deleted Successfully")
        } catch (e: Exception) {
            ResultFavorites.Error("Error: $e")
        }
    }

    override suspend fun getFavorites(): ResultAddedFavorites {
        return try {
            val response = foodDAO.getAllFavorites()
            ResultAddedFavorites.Success(response)
        } catch (e: Exception) {
            ResultAddedFavorites.Error("Error: $e")
        }
    }



}