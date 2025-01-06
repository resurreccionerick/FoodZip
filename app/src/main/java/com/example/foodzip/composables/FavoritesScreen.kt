package com.example.foodzip.composables

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.foodzip.composables.items.FavoriteItem
import com.example.foodzip.domain.FoodViewModel
import com.example.foodzip.models.ResultAddedFavorites


@Composable
fun FavoritesScreen(
    navController: NavController,
    viewModel: FoodViewModel = hiltViewModel(),
    context: Context = LocalContext.current,
) {

    val favorites = viewModel.addedFavorites.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getAllFavorites()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        when (val result = favorites.value) {
            is ResultAddedFavorites.Error -> Toast.makeText(context, result.msg, Toast.LENGTH_SHORT)
                .show()

            ResultAddedFavorites.Loading -> CircularProgressIndicator()
            is ResultAddedFavorites.Success -> {
                val meals by result.meal.collectAsState(initial = emptyList())
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(meals) { meal ->
                        FavoriteItem(
                            navController,
                            context = context,
                            meals = meal
                        )
                    }
                }
            }
        }

    }

}