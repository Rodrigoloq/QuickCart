package com.rodrigoloq.quickcart.feature.cart.viewmodel

import com.rodrigoloq.quickcart.data.room.entities.CartProducts

data class CartUIState(val cartProducts: List<CartProducts> = listOf(),
    val inProgress: Boolean = false,
    val errorMessage: String = "")
