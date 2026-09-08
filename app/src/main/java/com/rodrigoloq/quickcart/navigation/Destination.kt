package com.rodrigoloq.moviepedia.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ManageSearch
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ManageSearch
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

enum class Destination(val route: String,
                       val label: String,
                       val icon: ImageVector,
                       val contentDescription: String) {
    HOME("nav_home","Home", Icons.Outlined.Home,"Home view"),
    CATEGORIES("nav_categories", "Catalog", Icons.AutoMirrored.Default.ManageSearch,"Categories View"),
    CART("nav_cart","Cart", Icons.Outlined.ShoppingCart,"Cart view"),
    FAVORITES("nav_favorites","Favorites", Icons.Outlined.FavoriteBorder,"Favorites view"),
    PROFILE("nav_profile","Profile", Icons.Outlined.PersonOutline,"Profile view"),
}