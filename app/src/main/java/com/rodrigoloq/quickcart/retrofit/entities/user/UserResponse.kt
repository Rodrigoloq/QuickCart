package com.rodrigoloq.quickcart.retrofit.entities.user

data class UserResponse(var id: Int = 0,
    val firstName: String = "",
    val lastName: String = "",
    val phone: String = "",
    val email: String = "",
    val username: String = "",
    val password: String = "",
    val address: Address = Address(),
    val bank: Bank = Bank())
