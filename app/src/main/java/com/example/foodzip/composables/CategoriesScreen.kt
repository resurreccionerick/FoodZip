package com.example.foodzip.composables

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.foodzip.domain.FoodViewModel
import com.example.foodzip.models.ResultCategoryList

@Composable
fun CategoriesScreen(
    navController: NavController,
    foodViewModel: FoodViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {

    var categoriesState = foodViewModel.categoriesState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        LaunchedEffect(Unit) {
            foodViewModel.getCategories()
        }

        when (val result = categoriesState.value) {
            is ResultCategoryList.Loading -> CircularProgressIndicator()

            is ResultCategoryList.Success ->
                LazyColumn(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                )
                {
                    items(result.category) { cat ->
                        CategoriesItem(
                            navController,
                            viewModel = foodViewModel,
                            context = context,
                            category = cat
                        )
                    }
                }

            is ResultCategoryList.Error -> Log.d(
                "LIST OF CATEGORIES ERROR:",
                "ERROR: " + result.msg
            )
        }

    }

}