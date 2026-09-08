package com.rodrigoloq.quickcart.categories.viewmodel

import com.rodrigoloq.quickcart.retrofit.entities.product.Product

data class CategoriesUIState(val products: List<Product> = emptyList(),
                             val searchProducts: List<Product> = emptyList(),
                             val inProgressProducts: Boolean = false,
                             val inProgressSearchProducts: Boolean = false,
                             val errorMessageProducts: String = "",
                             val errorMessageSearchProducts: String = "",
                             val addToCartMessage: String = "")
