package com.rodrigoloq.quickcart.cart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigoloq.quickcart.cart.model.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {
    private val repository = CartRepository()

    private val _uiState = MutableStateFlow(CartUIState())

    val uiState: StateFlow<CartUIState> = _uiState.asStateFlow()

    init {
        getAllProductsFromCart()
    }

    fun getAllProductsFromCart(){
        _uiState.update { it.copy(inProgress = true) }
        viewModelScope.launch {
            repository.getAllProductsFromCart { response ->
                if(response != null){
                    _uiState.update { it.copy(cartProducts = response) }
                } else {
                    _uiState.update { it.copy(errorMessage = "Error al mostrar los productos") }
                }
            }
            _uiState.update { it.copy(inProgress = false) }
        }
    }

    fun modifyQuantity(productId: Int, isSum: Boolean){
        viewModelScope.launch {
            repository.modifyQuantity(productId, isSum){response ->
                if (response == null){
                    _uiState.update { it.copy(errorMessage = "Error al agregar/quitar productos") }
                }
            }
            getAllProductsFromCart()
        }
    }

    fun clearCart(){
        viewModelScope.launch {
            repository.clearCart { response ->
                if (!response){
                    _uiState.update { it.copy(errorMessage = "Error al vaciar el carrito") }
                }
            }
            getAllProductsFromCart()
        }
    }

    fun removeProductFromCart(productId: Int){
        viewModelScope.launch {
            repository.removeProductFromCart(productId){response ->
                if (!response){
                    _uiState.update { it.copy(errorMessage = "Error al eliminar el producto") }
                }
            }
            getAllProductsFromCart()
        }
    }
}