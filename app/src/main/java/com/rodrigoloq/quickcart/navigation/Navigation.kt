package com.rodrigoloq.moviepedia.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rodrigoloq.quickcart.cart.CartView
import com.rodrigoloq.quickcart.categories.CategoriesView
import com.rodrigoloq.quickcart.favorites.FavoritesView
import com.rodrigoloq.quickcart.home.HomeView
import com.rodrigoloq.quickcart.profile.ProfileView


@Composable
fun AppNavHost(innerNavController: NavHostController,
               rootNavController: NavController,
               startDestination: Destination,
               modifier: Modifier = Modifier,
               onClickCart: () -> Unit){
    NavHost(navController = innerNavController,
        startDestination = startDestination.route){
        Destination.entries.forEach { destination ->
            composable(destination.route){
                when(destination){
                    Destination.HOME -> HomeView(innerNavController, rootNavController,modifier){
                        onClickCart()
                    }
                    Destination.CATEGORIES -> CategoriesView(rootNavController, modifier)
                    Destination.FAVORITES-> FavoritesView(rootNavController,modifier)
                    Destination.CART -> CartView(rootNavController,modifier)
                    Destination.PROFILE -> ProfileView(rootNavController, modifier)
                }
            }
        }
    }
}