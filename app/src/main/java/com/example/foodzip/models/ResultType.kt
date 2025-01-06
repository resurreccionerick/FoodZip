package com.example.foodzip.models


import com.example.pagkain_mvvm.models.category.CategoryList
import com.example.pagkain_mvvm.models.category.categoryfood.Meal
import com.example.pagkain_mvvm.models.popular.PopularMeal
import com.example.pagkain_mvvm.models.random.MealsItem
import kotlinx.coroutines.flow.Flow

sealed class ResultType {
    data class Success(val meal: MealsItem) : ResultType()
    data class Error(val msg: String) : ResultType()
    data object Loading : ResultType()
}

sealed class ResultPopularList {
    data class Success(val meal: List<PopularMeal>) : ResultPopularList()
    data class Error(val msg: String) : ResultPopularList()
    data object Loading : ResultPopularList()
}

sealed class ResultCategoryList {
    data class Success(val category: List<CategoryList>) : ResultCategoryList()
    data class Error(val msg: String) : ResultCategoryList()
    data object Loading : ResultCategoryList()
}

sealed class ResultCategoryInfoList {
    data class Success(val category: List<Meal>) : ResultCategoryInfoList()
    data class Error(val msg: String) : ResultCategoryInfoList()
    data object Loading : ResultCategoryInfoList()
}

sealed class ResultFavorites {
    data class Success(val msg: String) : ResultFavorites()
    data class Error(val msg: String) : ResultFavorites()
    object Loading : ResultFavorites()
}

sealed class ResultAddedFavorites {
    data class Success(val meal: Flow<List<MealsItem>>) : ResultAddedFavorites()
    data class Error(val msg: String) : ResultAddedFavorites()
    data object Loading : ResultAddedFavorites()
}

sealed class ResultSearch {
    data class Success(val meal: List<MealsItem>) : ResultSearch()
    data class Error(val msg: String) : ResultSearch()
    object Loading : ResultSearch()
}
