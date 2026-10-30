package com.rodrigoloq.quickcart.feature.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigoloq.quickcart.feature.home.model.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState: StateFlow<HomeUIState> = _uiState.asStateFlow()

    init {
        getCartSize()
    }

    fun getProductsByCategory(categoryName: String){
        _uiState.update { it.copy(inProgressProducts = true) }
        viewModelScope.launch {
            repository.getProductsByCategory(categoryName){response ->
                if (response != null){
                    _uiState.update { it.copy(products = response) }
                    Log.i("RLTAG", "getProductsByCategory: $response")
                }
            }
            _uiState.update { it.copy(inProgressProducts = false) }
        }
    }

    fun searchProducts(searchValue: String){
        _uiState.update { it.copy(inProgressSearchProducts = true) }
        viewModelScope.launch {
            repository.searchProducts(searchValue){response ->
                if (response != null){
                    _uiState.update { it.copy(searchProducts = response) }
                    Log.i("RLTAG", "getProductsByCategory: $response")
                }
            }
            _uiState.update { it.copy(inProgressSearchProducts = false) }
        }
    }

    fun getCartSize(){
        viewModelScope.launch {
            repository.getCartSize { size ->
                _uiState.update { it.copy(cartSize = size) }
            }
        }
    }
}