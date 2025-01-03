package com.example.foodzip.models

import com.example.pagkain_mvvm.models.random.MealsItem

sealed class ResultType {
    data class Success(val meal: MealsItem) : ResultType()
    data class Error(val msg: String) : ResultType()
    object Loading : ResultType()
}