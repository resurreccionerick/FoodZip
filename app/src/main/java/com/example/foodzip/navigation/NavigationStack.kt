package com.example.foodzip.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodzip.composables.BottomNavBar
import com.example.foodzip.composables.HomeScreen

@Composable
fun NavigationStack() {
    val navController = rememberNavController()

    Scaffold(bottomBar = {
        BottomNavBar(navController = navController)
    }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home_screen",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home_screen") {
                HomeScreen(navController = navController)
            }

            composable("favorite_screen") {
                HomeScreen(navController = navController)
            }

            composable("settings_screen") {
                HomeScreen(navController = navController)
            }
        }
    }

}