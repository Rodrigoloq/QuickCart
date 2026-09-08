package com.rodrigoloq.quickcart.home.viewmodel

import com.rodrigoloq.quickcart.retrofit.entities.product.Product

data class HomeUIState(val products: List<Product> = emptyList(),
                       val searchProducts: List<Product> = emptyList(),
                       val inProgressProducts: Boolean = false,
                       val inProgressSearchProducts: Boolean = false,
                       val cartSize: Int = 0)
