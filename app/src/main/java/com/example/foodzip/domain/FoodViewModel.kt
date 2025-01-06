package com.example.foodzip.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodzip.models.ResultAddedFavorites
import com.example.foodzip.models.ResultCategoryInfoList
import com.example.foodzip.models.ResultCategoryList
import com.example.foodzip.models.ResultFavorites
import com.example.foodzip.models.ResultPopularList
import com.example.foodzip.models.ResultSearch
import com.example.foodzip.models.ResultType
import com.example.pagkain_mvvm.models.random.MealsItem
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
    private val _favoritesState = MutableStateFlow<ResultFavorites>(ResultFavorites.Loading)
    private val _addedFavoritesState =
        MutableStateFlow<ResultAddedFavorites>(ResultAddedFavorites.Loading)
    private val _searchState = MutableStateFlow<ResultSearch>(ResultSearch.Loading)

    val mealState: StateFlow<ResultType> get() = _mealState
    val mealListState: StateFlow<ResultPopularList> get() = _mealListState
    val categoriesState: StateFlow<ResultCategoryList> get() = _categoriesState
    val categoryFoodState: StateFlow<ResultCategoryInfoList> get() = _categoryFoodState
    val favoritesState: StateFlow<ResultFavorites> get() = _favoritesState
    val addedFavorites: StateFlow<ResultAddedFavorites> get() = _addedFavoritesState
    val searchState: StateFlow<ResultSearch> get() = _searchState

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

    fun addFavorite(fav: MealsItem) {
        viewModelScope.launch {
            _favoritesState.value = repository.insertFavorite(fav)
            repository.insertFavorite(fav)
        }
    }

    fun deleteFavorite(fav: MealsItem) {
        viewModelScope.launch {
            repository.deleteFavorite(fav)
        }
    }

    fun getAllFavorites() {
        viewModelScope.launch {
            _addedFavoritesState.value = repository.getFavorites()
        }
    }

    fun searchMeal(meal: String) {
        viewModelScope.launch {
            _searchState.value = repository.searchMeal(meal)
        }
    }
}