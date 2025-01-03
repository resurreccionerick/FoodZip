package com.example.foodzip

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodzip.domain.FoodRepository
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
    val mealState: StateFlow<ResultType> get() = _mealState

    fun getRandomMeal() {
        viewModelScope.launch {
            _mealState.value = repository.getRandomMeal()
        }
    }
}