package com.rodrigoloq.quickcart.retrofit.entities.product

data class Product(var id: Int = 0,
    var title: String = "",
    var description: String = "",
    var category: String = "",
    var price: Double = 0.0,
    var discountPercentage: Double = 0.0,
    var rating: Double = 0.0,
    var stock: Int = 0,
    var sku: String = "",
    var warrantyInformation: String = "",
    var shippingInformation: String = "",
    var availabilityStatus: String = "",
    var reviews: List<Review> = listOf(),
    var returnPolicy: String = "",
    var images: List<String> = listOf(),
    var thumbnail: String = "")