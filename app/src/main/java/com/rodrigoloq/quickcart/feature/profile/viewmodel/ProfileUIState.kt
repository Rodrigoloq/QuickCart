package com.rodrigoloq.quickcart.feature.profile.viewmodel

import com.rodrigoloq.quickcart.data.retrofit.entities.user.UserResponse

data class ProfileUIState(val user: UserResponse = UserResponse(),
                          val inProgress: Boolean = false,
                          val errorMessage: String = "")