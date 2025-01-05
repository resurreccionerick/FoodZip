package com.example.foodzip.composables.items

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.foodzip.domain.FoodViewModel
import com.example.foodzip.models.ResultFavorites
import com.example.foodzip.models.ResultType

@Composable
fun MealItemDetails(
    navController: NavController,
    viewModel: FoodViewModel = hiltViewModel(),
    context: Context = LocalContext.current,
    mealID: String
) {
    val mealDetailsState = viewModel.mealState.collectAsState()
    val favoritesState = viewModel.favoritesState.collectAsState()

    // Show a Toast for success or error messages when the state changes
    LaunchedEffect(favoritesState.value) {
        when (val result = favoritesState.value) {
            is ResultFavorites.Success -> {
                Toast.makeText(context, result.msg, Toast.LENGTH_SHORT).show()
            }

            is ResultFavorites.Error -> {
                Toast.makeText(context, result.msg, Toast.LENGTH_SHORT).show()
            }

            is ResultFavorites.Loading -> {
                Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Fetch meal details when the screen is launched
    LaunchedEffect(mealID) {
        viewModel.getMealInfo(mealID)
    }

    // Handle the state
    when (val result = mealDetailsState.value) {
        is ResultType.Loading -> {
            CircularProgressIndicator()
        }

        is ResultType.Success -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = result.meal.strMealThumb, // Pass the URL here
                    contentDescription = "Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentScale = ContentScale.Crop
                )

                Row(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row() {
                            Text(
                                text = "Area: ${result.meal.strArea}",
                                modifier = Modifier
                                    .weight(1f), fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Category: ${result.meal.strCategory}",
                                modifier = Modifier
                                    .weight(1f), fontWeight = FontWeight.Bold
                            )
                        }
                        Row() {
                            Text(text = "Link: ")
                            Text(text = "${result.meal.strSource}", color = Color.Blue)
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row() {
                            Button(
                                onClick = {
                                    val youtubeUrl = result.meal.strYoutube // Your YouTube URL
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
                                    context.startActivity(intent)
                                },
                                colors = ButtonDefaults.buttonColors(Color.Red),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(text = "Tutorial Video")
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Button(
                                onClick = {
                                    viewModel.addFavorite(result.meal) // This triggers the update in favoritesState
                                },
                                colors = ButtonDefaults.buttonColors(Color.Blue),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(text = "Add to favorite")
                            }
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        Text(text = "Instructions: ", fontWeight = FontWeight.Bold)

                        Text(result.meal.strInstructions, modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }

        is ResultType.Error -> {
            // Use LaunchedEffect to show a Toast for a side effect
            LaunchedEffect(result.msg) {
                Toast.makeText(context, "ERROR: ${result.msg}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

//@Composable
//fun MealItemDetails(
//    navController: NavController,
//    viewModel: FoodViewModel = hiltViewModel(),
//    context: Context = LocalContext.current,
//    mealID: String
//) {
//    val mealDetailsState = viewModel.mealState.collectAsState()
//    val favoritesState = viewModel.favoritesState.collectAsState()
//
//    // Show a Toast for success or error messages
//    favoritesState.value.let { result ->
//        when (result) {
//            is ResultFavorites.Success -> {
//                Toast.makeText(LocalContext.current, result.msg, Toast.LENGTH_SHORT).show()
//            }
//
//            is ResultFavorites.Error -> {
//                Toast.makeText(LocalContext.current, result.msg, Toast.LENGTH_SHORT).show()
//            }
//
//            is ResultFavorites.Loading -> {
//                CircularProgressIndicator()
//            }
//        }
//    }
//
//
//    // Fetch meal details when the screen is launched
//    LaunchedEffect(mealID) {
//        viewModel.getMealInfo(mealID)
//    }
//
//    // Handle the state
//    when (val result = mealDetailsState.value) {
//        is ResultType.Loading -> {
//            CircularProgressIndicator()
//        }
//
//        is ResultType.Success -> {
//            Column(
//                modifier = Modifier.fillMaxSize(),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                AsyncImage(
//                    model = result.meal.strMealThumb, // Pass the URL here
//                    contentDescription = "Image",
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(300.dp),
//                    contentScale = ContentScale.Crop
//                )
//
//                Row(
//                    modifier = Modifier
//                        .align(Alignment.CenterHorizontally)
//                        .padding(16.dp)
//                        .fillMaxWidth(),
//                    horizontalArrangement = Arrangement.Center
//                ) {
//                    Column(
//                        modifier = Modifier
//                            .weight(1f)
//                            .verticalScroll(rememberScrollState()),
//                        horizontalAlignment = Alignment.CenterHorizontally
//
//                    )
//                    {
//                        Row() {
//                            Text(
//                                text = "Area: ${result.meal.strArea}",
//                                modifier = Modifier
//                                    .weight(1f), fontWeight = FontWeight.Bold
//                            )
//                            Text(
//                                text = "Category: ${result.meal.strCategory}",
//                                modifier = Modifier
//                                    .weight(1f), fontWeight = FontWeight.Bold
//                            )
//                        }
//                        Row() {
//                            Text(text = "Link: ")
//                            Text(text = "${result.meal.strSource}", color = Color.Blue)
//                        }
//
//                        Spacer(modifier = Modifier.height(16.dp))
//
//                        Row() {
//                            Button(
//                                onClick = {
//                                    val youtubeUrl = result.meal.strYoutube // Your YouTube URL
//                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
//                                    context.startActivity(intent)
//                                },
//                                colors = ButtonDefaults.buttonColors(Color.Red),
//                                modifier = Modifier.weight(1f)
//                            ) {
//                                Text(
//                                    text = "Tutorial Video"
//                                )
//                            }
//
//                            Spacer(modifier = Modifier.width(8.dp))
//
//
//                            Button(
//                                onClick = {
//                                    viewModel.addFavorite(result.meal)
//                                },
//                                colors = ButtonDefaults.buttonColors(Color.Blue),
//                                modifier = Modifier.weight(1f)
//                            ) {
//                                Text(
//                                    text = "Add to favorite"
//                                )
//                            }
//                        }
//
//
//                        Spacer(modifier = Modifier.height(32.dp))
//
//                        Text(text = "Instructions: ", fontWeight = FontWeight.Bold)
//
//                        Text(result.meal.strInstructions, modifier = Modifier.padding(8.dp))
//                    }
//                }
//            }
//        }
//
//        is ResultType.Error -> {
//            // Use LaunchedEffect to show a Toast for a side effect
//            LaunchedEffect(result.msg) {
//                Toast.makeText(context, "ERROR: ${result.msg}", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//}
