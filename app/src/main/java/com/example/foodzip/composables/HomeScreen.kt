package com.example.foodzip.composables

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.foodzip.R
import com.example.foodzip.composables.items.MealItem
import com.example.foodzip.composables.items.MealItemRow
import com.example.foodzip.domain.FoodViewModel
import com.example.foodzip.models.ResultPopularList
import com.example.foodzip.models.ResultType

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: FoodViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {
    var randomMealState = viewModel.mealState.collectAsState()
    var mealListState = viewModel.mealListState.collectAsState()

    var search by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.getRandomMeal()
        viewModel.getMealListPopular()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
    )
    {
        Row(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .height(80.dp)
        ) {
            Image(
                painterResource(R.drawable.foodzip_logo),
                contentDescription = "",
                contentScale = ContentScale.Fit,
            )
            OutlinedTextField(modifier = Modifier.weight(1f),
                value = search,
                onValueChange = { search = it },
                label = { Text(text = "Search...") }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "What would you like to eat?")

        when (val result = randomMealState.value) {
            is ResultType.Loading -> CircularProgressIndicator()
            is ResultType.Success -> MealItem(
                navController = navController,
                viewModel = viewModel,
                context = context,
                img = result.meal.strMealThumb,
                label = result.meal.strMeal,
                mealId = result.meal.idMeal
            )

            is ResultType.Error -> Log.d("RANDOM MEAL ERROR:", "ERROR: " + result.msg)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "Popular Meals:")

        when (val result = mealListState.value) {
            is ResultPopularList.Loading -> CircularProgressIndicator()

            is ResultPopularList.Success -> LazyRow(
                contentPadding = PaddingValues(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(result.meal) { meal ->
                    MealItemRow(
                        navController,
                        viewModel = viewModel,
                        context = context,
                        meal = meal
                    )
                }
            }

            is ResultPopularList.Error -> Log.d("LIST OF MEAL ERROR:", "ERROR: " + result.msg)
        }


    }
}