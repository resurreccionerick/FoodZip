package com.example.foodzip.composables

import android.content.Context
import android.widget.Toast
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.foodzip.domain.FoodViewModel
import com.example.foodzip.models.ResultType

@Composable
fun MealItemDetails(
    navController: NavController,
    viewModel: FoodViewModel = hiltViewModel(),
    context: Context = LocalContext.current,
    mealID: String
) {
    val mealDetailsState = viewModel.mealState.collectAsState()

    // Fetch meal details when the screen is launched
    LaunchedEffect(mealID) {
        Toast.makeText(context, "DETAILS ID: $mealID", Toast.LENGTH_SHORT).show()
        viewModel.getMealInfo(mealID)
    }

    // Handle the state
    when (val result = mealDetailsState.value) {
        is ResultType.Loading -> {
            CircularProgressIndicator()
        }

        is ResultType.Success -> {
            // Use LaunchedEffect to show a Toast for a side effect
            LaunchedEffect(result.meal.idMeal) {
                Toast.makeText(
                    context,
                    "SUCCESS: ${result.meal.strMeal}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            // Display meal details here
            Text(text = "Meal Name: ${result.meal.strMeal}")
        }

        is ResultType.Error -> {
            // Use LaunchedEffect to show a Toast for a side effect
            LaunchedEffect(result.msg) {
                Toast.makeText(context, "ERROR: ${result.msg}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
