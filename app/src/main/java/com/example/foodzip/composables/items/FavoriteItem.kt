package com.example.foodzip.composables.items

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.foodzip.composables.MyAlertDialog
import com.example.foodzip.domain.FoodViewModel
import com.example.foodzip.models.ResultFavorites
import com.example.pagkain_mvvm.models.random.MealsItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoriteItem(
    navController: NavController,
    viewModel: FoodViewModel = hiltViewModel(),
    context: Context,
    meals: MealsItem
) {
    val showDeleteDialog = remember { mutableStateOf(false) }
    val favorites = viewModel.favoritesState.collectAsState()

    if (showDeleteDialog.value) {
        MyAlertDialog(showDeleteDialog, mealsItem = meals)
    }

    LaunchedEffect(favorites.value) {
        when(val result = favorites.value){
            is ResultFavorites.Error -> Toast.makeText(context, result.msg, Toast.LENGTH_SHORT).show()
            ResultFavorites.Loading -> Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
            is ResultFavorites.Success -> Toast.makeText(context, result.msg, Toast.LENGTH_SHORT).show()
        }
    }

    Column() {
        Card(
            modifier = Modifier.combinedClickable(
                onClick = { navController.navigate("meal_details/${meals.idMeal}") },
                onLongClick = {
                    showDeleteDialog.value = true
                })
        ) {
            Column(

            ) {
                AsyncImage(
                    model = meals.strMealThumb, // Pass the URL here
                    contentDescription = "Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )

                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally),
                    text = meals.strMeal,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}