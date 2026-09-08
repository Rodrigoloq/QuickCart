package com.rodrigoloq.quickcart.cart.viewmodel

import com.rodrigoloq.quickcart.room.entities.CartProducts

data class CartUIState(val cartProducts: List<CartProducts> = listOf(),
    val inProgress: Boolean = false,
    val errorMessage: String = "")
