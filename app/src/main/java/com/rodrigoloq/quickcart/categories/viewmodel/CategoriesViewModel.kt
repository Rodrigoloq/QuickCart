package com.rodrigoloq.quickcart.categories.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigoloq.quickcart.categories.model.CategoriesRepository
import com.rodrigoloq.quickcart.room.entities.CartProducts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val repository: CategoriesRepository
) : ViewModel(){

    private val _uiState = MutableStateFlow(CategoriesUIState())

    val uiState: StateFlow<CategoriesUIState> = _uiState.asStateFlow()

    fun getProductsByCategory(categoryName: String){
        _uiState.update { it.copy(inProgressProducts = true) }
        viewModelScope.launch {
            repository.getProductsByCategory(categoryName){response ->
                if (response != null){
                    _uiState.update { it.copy(products = response) }
                    Log.i("RLTAG", "getProductsByCategory: $response")
                } else {
                    _uiState.update {
                        it.copy(errorMessageProducts = "Error al mostrar los productos") }
                }
                _uiState.update { it.copy(inProgressProducts = false) }
            }
        }
    }

    fun searchProducts(searchValue: String){
        _uiState.update { it.copy(inProgressSearchProducts = true) }
        viewModelScope.launch {
            repository.searchProducts(searchValue){response ->
                if (response != null){
                    _uiState.update { it.copy(searchProducts = response) }
                    Log.i("RLTAG", "getProductsByCategory: $response")
                } else {
                    _uiState.update {
                        it.copy(errorMessageSearchProducts = "Error al mostrar los productos") }
                }
                _uiState.update { it.copy(inProgressSearchProducts = false) }
            }
        }
    }

    fun addProductToCart(product: CartProducts){
        viewModelScope.launch {
            repository.addProductToCart(product){result ->
                if (result){
                    _uiState.update { it.copy(addToCartMessage = "Producto agregado correctamente") }
                } else {
                    _uiState.update { it.copy(addToCartMessage = "Error al agregar producto al carrito") }
                }
            }
        }
    }
}