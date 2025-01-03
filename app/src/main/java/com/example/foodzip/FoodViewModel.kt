package com.example.foodzip

import androidx.lifecycle.ViewModel
import com.example.foodzip.domain.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FoodViewModel @Inject constructor(private val repository: FoodRepository) : ViewModel() {

}