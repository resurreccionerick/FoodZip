package com.example.foodzip.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodzip.composables.BottomNavBar
import com.example.foodzip.composables.CategoriesScreen
import com.example.foodzip.composables.HomeScreen
import com.example.foodzip.composables.items.CategoryFoodItem
import com.example.foodzip.composables.items.MealItemDetails

@Composable
fun NavigationStack() {
    val navController = rememberNavController()
    var showBtmNavBar by rememberSaveable { mutableStateOf(false) }

    Scaffold(bottomBar = {
        if (showBtmNavBar) {
            BottomNavBar(navController = navController)
        }

    }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home_screen",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home_screen") {
                showBtmNavBar = true
                HomeScreen(navController = navController)
            }

            composable("favorite_screen") {
                showBtmNavBar = true
                HomeScreen(navController = navController)
            }

            composable("categories_screen") {
                showBtmNavBar = true
                CategoriesScreen(navController = navController)
            }

            composable("meal_details/{mealId}") { navBackEntry ->
                showBtmNavBar = false
                val mealId = navBackEntry.arguments?.getString("mealId")
                mealId?.let { MealItemDetails(navController, mealID = it) }

            }

            composable("category_food/{categoryId}") { navBackStackEntry ->
                showBtmNavBar = false
                val categoryId = navBackStackEntry.arguments?.getString("categoryId")
                categoryId?.let { CategoryFoodItem(navController, categoryId = it) }
            }
        }
    }

}