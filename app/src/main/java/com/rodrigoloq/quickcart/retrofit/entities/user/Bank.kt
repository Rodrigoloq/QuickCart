package com.rodrigoloq.quickcart.retrofit.entities.user

data class Bank(var cardExpire: String = "",
    var cardNumber: String = "",
    var cardType: String = "",
    var currency: String = "")
