package com.rodrigoloq.quickcart.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rodrigoloq.moviepedia.navigation.NavigationBarView
import com.rodrigoloq.quickcart.feature.product.ProductDetailView

@Composable
fun AppNavigation(modifier: Modifier = Modifier){
    val navController = rememberNavController()

    NavHost(navController = navController,
        startDestination = "main"){
        navigation(startDestination = "home", route = "main"){
            composable("home"){
                NavigationBarView(navController)
            }
            composable("product" + "/{pid}", arguments = listOf(navArgument(name = "pid"){
                type = NavType.StringType
            })) {
                ProductDetailView(navController,it.arguments?.getString("pid"),modifier)
            }
        }
    }
}