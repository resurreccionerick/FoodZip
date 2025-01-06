package com.example.foodzip.composables

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.example.foodzip.models.ResultSearch
import com.example.foodzip.models.ResultType


@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: FoodViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {
    val randomMealState = viewModel.mealState.collectAsState()
    val mealListState = viewModel.mealListState.collectAsState()
    val searchState = viewModel.searchState.collectAsState()

    var search by rememberSaveable { mutableStateOf("") }
    var searchOn by rememberSaveable { mutableStateOf(false) }

    // Trigger search or fetch meals when search query changes
    LaunchedEffect(search) {
        viewModel.getRandomMeal()
        viewModel.getMealListPopular()
        if (search.isNotBlank()) {
            viewModel.searchMeal(search)
            searchOn = true // Enable search view
        } else {
            searchOn = false // Return to normal view
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // Header with logo and search bar
        HeaderSection(search = search, onSearchChanged = { query ->
            search = query
        })

        if (!searchOn) { // Normal view
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "What would you like to eat?")

            // Random Meal Section
            MealSection(
                result = randomMealState.value,
                onError = { msg -> Log.d("RANDOM MEAL ERROR:", msg) },
                navController = navController,
                viewModel = viewModel,
                context = context
            )

            Spacer(modifier = Modifier.height(32.dp))
            Text(text = "Popular Meals:")

            // Popular Meals Section
            PopularMealsSection(
                result = mealListState.value,
                onError = { msg -> Log.d("LIST OF MEAL ERROR:", msg) },
                navController = navController,
                viewModel = viewModel,
                context = context
            )
        } else { // Search results view
            SearchResultSection(
                result = searchState.value,
                navController = navController,
                viewModel = viewModel,
                context = context
            )
        }
    }
}

@Composable
fun HeaderSection(search: String, onSearchChanged: (String) -> Unit) {
    Row(
        modifier = Modifier
            .padding(bottom = 8.dp)
            .height(80.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.foodzip_logo),
            contentDescription = "",
            contentScale = ContentScale.Fit,
        )
        OutlinedTextField(
            value = search,
            onValueChange = onSearchChanged,
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun MealSection(
    result: ResultType,
    onError: (String) -> Unit,
    navController: NavController,
    viewModel: FoodViewModel,
    context: Context
) {
    when (result) {
        is ResultType.Loading -> CircularProgressIndicator()
        is ResultType.Success -> MealItem(
            navController = navController,
            viewModel = viewModel,
            context = context,
            img = result.meal.strMealThumb,
            label = result.meal.strMeal,
            mealId = result.meal.idMeal
        )
        is ResultType.Error -> onError(result.msg)
    }
}

@Composable
fun SearchResultSection(
    result: ResultSearch,
    navController: NavController,
    viewModel: FoodViewModel,
    context: Context
) {
    when (result) {
        is ResultSearch.Loading -> Text("Loading...")
        is ResultSearch.Error -> {
            Toast.makeText(LocalContext.current, result.msg, Toast.LENGTH_SHORT).show()
        }
        is ResultSearch.Success -> {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(result.meal) { meal ->
                    MealItem(
                        navController = navController,
                        viewModel = viewModel,
                        context = context,
                        img = meal.strMealThumb,
                        label = meal.strMeal,
                        mealId = meal.idMeal
                    )
                }
            }
        }
    }
}

@Composable
fun PopularMealsSection(
    result: ResultPopularList,
    onError: (String) -> Unit,
    navController: NavController,
    viewModel: FoodViewModel,
    context: Context
) {
    when (result) {
        is ResultPopularList.Loading -> CircularProgressIndicator()
        is ResultPopularList.Success -> LazyRow(
            contentPadding = PaddingValues(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(result.meal) { meal ->
                MealItemRow(
                    navController = navController,
                    viewModel = viewModel,
                    context = context,
                    meal = meal
                )
            }
        }
        is ResultPopularList.Error -> onError(result.msg)
    }
}
