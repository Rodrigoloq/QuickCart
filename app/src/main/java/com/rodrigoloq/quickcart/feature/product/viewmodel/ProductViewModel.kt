package com.rodrigoloq.quickcart.feature.product.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigoloq.quickcart.feature.product.model.ProductRepository
import com.rodrigoloq.quickcart.data.room.entities.CartProducts
import com.rodrigoloq.quickcart.data.room.entities.Favorites
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductUIState())

    val uiState: StateFlow<ProductUIState> = _uiState.asStateFlow()

    fun getProductDetail(productId: String){
        _uiState.update { it.copy(inProgress = true) }
        viewModelScope.launch {
            repository.getProductDetail(productId){response ->
                if (response != null){
                    _uiState.update { it.copy(product = response) }
                } else {
                    _uiState.update {
                        it.copy(errorMessage = "Error displaying this product") }
                }
            }
            _uiState.update { it.copy(inProgress = false) }
        }
    }

    fun isProductFavorite(productId: String){
        viewModelScope.launch {
            repository.isProductFavorite(productId.toInt()){result ->
                if (result){
                    _uiState.update { it.copy(isFavorite = true) }
                }
            }
        }
    }

    fun addProductToFavorites(favorite: Favorites){
        viewModelScope.launch {
            repository.addProductToFavorites(favorite){result ->
                if (result){
                    _uiState.update { it.copy(isFavorite = true) }
                }
            }
        }
    }

    fun removeFavorite(productId: Int){
        viewModelScope.launch {
            repository.removeFavorite(productId){result ->
                if (result){
                    _uiState.update { it.copy(isFavorite = false) }
                }
            }
        }
    }

    fun addProductToCart(product: CartProducts){
        viewModelScope.launch {
            repository.addProductToCart(product){result ->
                if (result){
                    _uiState.update { it.copy(successMessage = "Product add to cart") }
                } else {
                    _uiState.update { it.copy(errorMessage = "This product could not be added") }
                }
            }
        }
    }

}