package com.example.foodzip.composables

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.foodzip.domain.FoodViewModel
import com.example.foodzip.models.ResultType

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: FoodViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {
    var randomMealState = viewModel.mealState.collectAsState()
    var search by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.getRandomMeal()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    )
    {
        Row(modifier = Modifier.padding( bottom = 8.dp)) {
            Text(
                text = "FoodZip",
                modifier = Modifier
                    .padding(end = 8.dp)
                    .align(Alignment.CenterVertically)
            )
            OutlinedTextField(modifier = Modifier.weight(1f),
                value = search,
                onValueChange = { search = it },
                label = { Text(text = "Search...") }
            )
        }


        when (val result = randomMealState.value) {
            is ResultType.Loading -> CircularProgressIndicator()
            is ResultType.Success -> MealItem(
                viewModel = viewModel,
                context = context,
                img = result.meal.strMealThumb,
                label = result.meal.strMeal
            )

            is ResultType.Error -> Log.d("RANDOM MEAL ERROR:", "ERROR: " + result.msg)

        }
    }
}