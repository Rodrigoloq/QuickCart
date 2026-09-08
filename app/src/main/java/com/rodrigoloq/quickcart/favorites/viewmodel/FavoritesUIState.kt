package com.rodrigoloq.quickcart.favorites.viewmodel

import com.rodrigoloq.quickcart.room.entities.Favorites

data class FavoritesUIState(val favorites: List<Favorites> = listOf(),
    val inProgress: Boolean = false,
    val errorMessage: String = "",
    val successMessage: String = "")
