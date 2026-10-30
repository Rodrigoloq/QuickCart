package com.rodrigoloq.quickcart.feature.profile.model

import android.util.Log
import com.rodrigoloq.quickcart.QuickCartApp
import com.rodrigoloq.quickcart.data.retrofit.UserService
import com.rodrigoloq.quickcart.data.retrofit.entities.user.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val userService: UserService
){
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