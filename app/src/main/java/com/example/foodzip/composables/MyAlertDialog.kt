package com.example.foodzip.composables

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.foodzip.domain.FoodViewModel
import com.example.pagkain_mvvm.models.random.MealsItem

@Composable
fun MyAlertDialog(
    shouldShowDialog: MutableState<Boolean>,
    viewModel: FoodViewModel = hiltViewModel(),
    mealsItem: MealsItem
) {
    if (shouldShowDialog.value) {
        AlertDialog( // 3
            onDismissRequest = {
                shouldShowDialog.value = false
            },
            title = { Text(text = "Delete Confirmation") },
            text = { Text(text = "Are you sure you want to delete?") },
            confirmButton = { // 6
                Button(
                    onClick = {
                        viewModel.deleteFavorite(mealsItem)
                        shouldShowDialog.value = false
                    }
                ) {
                    Text(
                        text = "Confirm",
                        color = Color.White
                    )
                }
            }
        )
    }
}