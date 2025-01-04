package com.example.foodzip.domain

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodzip.models.ResultCategoryInfoList
import com.example.foodzip.models.ResultCategoryList
import com.example.foodzip.models.ResultPopularList
import com.example.foodzip.models.ResultType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodViewModel @Inject constructor
    (private val repository: FoodRepository) : ViewModel() {

    private val _mealState = MutableStateFlow<ResultType>(ResultType.Loading)
    private val _mealListState = MutableStateFlow<ResultPopularList>(ResultPopularList.Loading)
    private val _categoriesState = MutableStateFlow<ResultCategoryList>(ResultCategoryList.Loading)
    private val _categoryFoodState =
        MutableStateFlow<ResultCategoryInfoList>(ResultCategoryInfoList.Loading)

    val mealState: StateFlow<ResultType> get() = _mealState
    val mealListState: StateFlow<ResultPopularList> get() = _mealListState
    val categoriesState: StateFlow<ResultCategoryList> get() = _categoriesState
    val categoryFoodState: StateFlow<ResultCategoryInfoList> get() = _categoryFoodState

    fun getRandomMeal() {
        viewModelScope.launch {
            _mealState.value = repository.getRandomMeal()
        }
    }

    fun getMealListPopular() {
        viewModelScope.launch {
            _mealListState.value = repository.getMealListPopular()
        }
    }

    fun getMealInfo(id: String) {
        viewModelScope.launch {
            _mealState.value = repository.getMealInfo(id)
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            _categoriesState.value = repository.getCategories()
        }
    }

    fun getCategoryFood(id: String) {
        viewModelScope.launch {
            _categoryFoodState.value = repository.getCategoriesFood(id)
        }
    }
}