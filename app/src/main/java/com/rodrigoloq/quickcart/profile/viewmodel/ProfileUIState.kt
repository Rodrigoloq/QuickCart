package com.rodrigoloq.quickcart.profile.viewmodel

import com.rodrigoloq.quickcart.retrofit.entities.user.UserResponse

data class ProfileUIState(val user: UserResponse = UserResponse(),
                          val inProgress: Boolean = false,
                          val errorMessage: String = "")