package com.rodrigoloq.quickcart.feature.profile.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigoloq.quickcart.feature.profile.model.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUIState())
    val uiState: StateFlow<ProfileUIState> = _uiState.asStateFlow()

    init {
        getUserById()
    }

    fun getUserById(){
        val userId = (1..208).random().toString()
        _uiState.update { it.copy(inProgress = true) }
        viewModelScope.launch {
            repository.getUserById(userId){response ->
                if (response != null){
                    _uiState.update { it.copy(user = response) }
                    Log.i("RLTAG", "getProductsByCategory: $response")
                } else {
                    _uiState.update {
                        it.copy(errorMessage = "Error al mostrar los productos") }
                }
                _uiState.update { it.copy(inProgress = false) }
            }
        }
    }
}