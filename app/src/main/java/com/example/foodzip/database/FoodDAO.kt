package com.example.foodzip.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pagkain_mvvm.models.random.MealsItem
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMeal(meal: MealsItem)

    @Delete
    suspend fun deleteMeal(meal: MealsItem)

    @Query("SELECT * FROM food_table")
    fun getAllFavorites(): Flow<List<MealsItem>>
}