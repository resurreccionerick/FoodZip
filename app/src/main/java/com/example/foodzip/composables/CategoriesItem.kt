package com.example.foodzip.composables

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
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
import com.example.pagkain_mvvm.models.category.CategoryList

@Composable
fun CategoriesItem(
    navController: NavController, viewModel: ViewModel, context: Context, category: CategoryList
) {
    Card(onClick = {
        navController.navigate("meal_details/${category.idCategory}")
    }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .align(alignment = Alignment.CenterHorizontally)
        )
        {
            AsyncImage(
                model = category.strCategoryThumb, contentDescription = category.strCategory,
                modifier =
                Modifier
                    .size(100.dp)
                    .padding(end = 8.dp)
            )

            Text(
                text = category.strCategory,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(alignment = Alignment.CenterVertically)
            )


        }
    }
}