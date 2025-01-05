package com.example.foodzip.di

import android.app.Application
import android.content.Context
import com.example.foodzip.database.FoodDAO
import com.example.foodzip.database.FoodDatabase
import com.example.foodzip.domain.FoodRepository
import com.example.foodzip.domain.FoodRepositoryImpl
import com.example.foodzip.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApi(): ApiService {
        return Retrofit.Builder().baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(apiService: ApiService, foodDAO: FoodDAO): FoodRepository {
        return FoodRepositoryImpl(foodDAO, apiService)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): FoodDatabase {
        return FoodDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideFoodDAO(database: FoodDatabase): FoodDAO {
        return database.foodDao()
    }
}