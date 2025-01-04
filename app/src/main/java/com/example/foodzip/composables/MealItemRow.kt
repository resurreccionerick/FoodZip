package com.example.foodzip.composables

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.pagkain_mvvm.models.popular.PopularMeal

@Composable
fun MealItemRow(
    navController: NavController,
    viewModel: ViewModel,
    context: Context,
    meal: PopularMeal
) {
    Card(onClick = {
        navController.navigate("meal_details/${meal.idMeal}")
    }) {
        Column {
            AsyncImage(
                model = meal.strMealThumb, // Pass the URL here
                contentDescription = "Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            //Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                text = meal.strMeal,
                fontWeight = FontWeight.Bold
            )
        }
    }
}