package com.example.foodzip.composables.items

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.foodzip.domain.FoodViewModel
import com.example.foodzip.models.ResultCategoryInfoList


@Composable
fun CategoryFoodItem(
    navController: NavController,
    foodViewModel: FoodViewModel = hiltViewModel(),
    context: Context = LocalContext.current,
    categoryId: String
) {
    val categoryFoodState = foodViewModel.categoryFoodState.collectAsState()

    LaunchedEffect(Unit) {
        foodViewModel.getCategoryFood(categoryId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "FOOD: ",
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
            fontWeight = FontWeight.Bold

        )
        when (val result = categoryFoodState.value) {
            ResultCategoryInfoList.Loading -> CircularProgressIndicator()

            is ResultCategoryInfoList.Success ->
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(result.category) { cat ->
                        CategoriesFoodItem(navController, foodViewModel, context, cat)
                    }
                }

            is ResultCategoryInfoList.Error -> Log.d(
                "LIST OF CATEGORY MEAL ERROR:",
                "ERROR: " + result.msg
            )
        }
    }
}