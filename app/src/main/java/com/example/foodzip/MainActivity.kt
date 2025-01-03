package com.example.foodzip

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.foodzip.models.ResultType
import com.example.pagkain_mvvm.models.random.MealsItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val viewModel = hiltViewModel<FoodViewModel>()
            val state = viewModel.mealState.collectAsState()

            // Trigger API call
            LaunchedEffect(Unit) {
                viewModel.getRandomMeal()
            }

            when (val result = state.value) {
                is ResultType.Loading -> CircularProgressIndicator()
                is ResultType.Success -> MealDisplay(result.meal)
                is ResultType.Error -> Log.d("RANDOM MEAL ERROR:", "ERROR: " + result.msg)

            }
        }
    }
}

@Composable
fun MealDisplay(meal: MealsItem) {
    Column {
        Text("Meal: ${meal.strMeal}")
        Text("Category: ${meal.strCategory}")
        Text("Instructions: ${meal.strInstructions}")

//        Image(
//            painter = painterResource(meal.strMealThumb.toInt()),
//            contentDescription = null
//        )
    }
}
