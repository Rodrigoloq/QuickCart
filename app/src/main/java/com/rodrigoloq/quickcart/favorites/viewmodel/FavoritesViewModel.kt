package com.rodrigoloq.quickcart.favorites.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigoloq.quickcart.favorites.model.FavoritesRepository
import com.rodrigoloq.quickcart.room.entities.CartProducts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: FavoritesRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(FavoritesUIState())

    val uiState: StateFlow<FavoritesUIState> = _uiState.asStateFlow()

    init {
        getAllFavorites()
    }

    fun getAllFavorites(){
        _uiState.update { it.copy(inProgress = true) }
        viewModelScope.launch {
            repository.getAllFavorites { result ->
                if(result != null){
                    _uiState.update { it.copy(favorites = result) }
                } else {
                    _uiState.update { it.copy(errorMessage = "Error al mostrar favoritos") }
                }
            }
            _uiState.update { it.copy(inProgress = false) }
        }
    }

    fun removeFavorite(productId: Int){
        viewModelScope.launch {
            repository.removeFavorite(productId){result ->
                if (!result){
                    _uiState.update { it.copy(errorMessage = "Error al quitar de favoritos") }
                }
            }
            getAllFavorites()
        }
    }

    fun clearFavorites(){
        viewModelScope.launch {
            repository.clearFavorites { result ->
                if (!result){
                    _uiState.update { it.copy(errorMessage = "Error al quitar de favoritos") }
                }
            }
            getAllFavorites()
        }
    }

    fun addProductToCart(product: CartProducts){
        viewModelScope.launch {
            repository.addProductToCart(product){result ->
                if (result){
                    _uiState.update { it.copy(successMessage = "Producto agregado correctamente") }
                } else {
                    _uiState.update { it.copy(errorMessage = "Error al agregar producto al carrito") }
                }
            }
        }
    }
}