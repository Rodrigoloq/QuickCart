package com.rodrigoloq.quickcart.profile.model

import android.util.Log
import com.rodrigoloq.quickcart.QuickCartApp
import com.rodrigoloq.quickcart.retrofit.UserService
import com.rodrigoloq.quickcart.retrofit.entities.user.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProfileRepository {
    private val userService: UserService by lazy {
        QuickCartApp.rdb.create(UserService::class.java)
    }

    suspend fun getUserById(userId: String,
                            onResult:(UserResponse?) -> Unit) =
        withContext(Dispatchers.IO){
        try {
            val response = userService.getUserById(userId)
            onResult(response)
        } catch (e: Exception) {
            onResult(null)
            Log.e("RLTAG", "getUserById: ${e.message}", )
        }
    }
}