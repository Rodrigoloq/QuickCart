package com.rodrigoloq.quickcart.data.retrofit

import com.rodrigoloq.quickcart.data.retrofit.entities.user.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {

    @GET("/users/{id}")
    suspend fun getUserById(@Path("id") userId: String): UserResponse
}