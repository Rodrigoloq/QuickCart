package com.rodrigoloq.quickcart.product.viewmodel

import com.rodrigoloq.quickcart.retrofit.entities.product.Product

data class ProductUIState(val product: Product = Product(),
                          val inProgress: Boolean = false,
                          val errorMessage: String = "",
                          val successMessage: String = "",
                          val isFavorite: Boolean = false)