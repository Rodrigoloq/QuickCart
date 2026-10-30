package com.rodrigoloq.quickcart.data.retrofit.entities.product

data class Review(var rating: Int = 0,
    var comment: String = "",
    var date: String = "",
    var reviewerName: String = "",
    var reviewerEmail: String = "")
