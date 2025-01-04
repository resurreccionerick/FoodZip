package com.example.foodzip.composables

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.foodzip.domain.FoodViewModel

@Composable
fun BottomNavBar(
    navController: NavController,
    viewModel: FoodViewModel = hiltViewModel()
) {
    val items = listOf(Screen.Home, Screen.Assessment, Screen.Settings)

    BottomNavigation {
        items.forEach { item ->
            BottomNavigationItem(
                selected = navController.currentDestination?.route == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        //avoid multiple copies of the same direction
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        // Retain state when switching
                        launchSingleTop = true
                        restoreState = true
                    }

                },
                icon = { Icon(item.icon, contentDescription = item.label) })

        }

    }
}

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    data object Home : Screen("home_screen", "Home", Icons.Default.Home)
    data object Assessment : Screen("favorite_screen", "Favorite", Icons.Default.Favorite)
    data object Settings : Screen("categories_screen", "Settings", Icons.Default.Settings)
}



