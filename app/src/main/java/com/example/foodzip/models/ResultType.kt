package com.example.foodzip.models

import com.example.pagkain_mvvm.models.category.Category
import com.example.pagkain_mvvm.models.category.CategoryList
import com.example.pagkain_mvvm.models.category.categoryfood.CategoryFood
import com.example.pagkain_mvvm.models.category.categoryfood.Meal
import com.example.pagkain_mvvm.models.popular.PopularMeal
import com.example.pagkain_mvvm.models.random.MealsItem

sealed class ResultType {
    data class Success(val meal: MealsItem) : ResultType()
    data class Error(val msg: String) : ResultType()
    object Loading : ResultType()
}

sealed class ResultPopularList {
    data class Success(val meal: List<PopularMeal>) : ResultPopularList()
    data class Error(val msg: String) : ResultPopularList()
    object Loading : ResultPopularList()
}

sealed class ResultCategoryList {
    data class Success(val category: List<CategoryList>) : ResultCategoryList()
    data class Error(val msg: String) : ResultCategoryList()
    object Loading : ResultCategoryList()
}

sealed class ResultCategoryInfoList {
    data class Success(val category: List<Meal>) : ResultCategoryInfoList()
    data class Error(val msg: String) : ResultCategoryInfoList()
    object Loading : ResultCategoryInfoList()
}