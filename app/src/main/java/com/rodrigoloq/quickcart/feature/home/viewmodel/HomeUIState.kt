package com.rodrigoloq.quickcart.feature.home.viewmodel

import com.rodrigoloq.quickcart.data.retrofit.entities.product.Product

data class HomeUIState(val products: List<Product> = emptyList(),
                       val searchProducts: List<Product> = emptyList(),
                       val inProgressProducts: Boolean = false,
                       val inProgressSearchProducts: Boolean = false,
                       val cartSize: Int = 0)
