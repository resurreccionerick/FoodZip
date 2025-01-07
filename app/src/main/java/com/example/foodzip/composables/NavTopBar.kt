package com.example.foodzip.composables

import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavTopBar(
    navController: NavController,
    modifier: Modifier = Modifier,
    title: String,
    canNavigateBack: Boolean,
    actions: @Composable () -> Unit = {}
) {
    if (canNavigateBack) {
        TopAppBar(
            title = {
                Text(
                    text = title, modifier = Modifier,
                    fontWeight = FontWeight.Bold
                )
            },
            actions = { actions() },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "back"
                    )
                }
            },
            modifier = modifier
        )
    } else {
        TopAppBar(
            title = {
                Text(text = title)
            },
            actions = { actions() },
            modifier = modifier
        )
    }
}