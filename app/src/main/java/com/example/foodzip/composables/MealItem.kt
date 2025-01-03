package com.example.foodzip.composables

import android.content.Context
import android.widget.Toast
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.foodzip.domain.FoodViewModel

@Composable
fun MealItem(
    navController: NavController,
    viewModel: FoodViewModel,
    context: Context,
    img: String,
    label: String,
    mealId: String
) {
    Card(onClick = {
        navController.navigate("meal_details/$mealId")
    }) {
        Column {
            AsyncImage(
                model = img, // Pass the URL here
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
                text = label,
                fontWeight = FontWeight.Bold
            )
        }
    }
}