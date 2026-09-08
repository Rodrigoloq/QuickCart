package com.rodrigoloq.quickcart.cart.model

import android.util.Log
import com.rodrigoloq.quickcart.QuickCartApp
import com.rodrigoloq.quickcart.room.CartProductsDao
import com.rodrigoloq.quickcart.room.entities.CartProducts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CartRepository {
    private val cartProductsDao: CartProductsDao by lazy {
        QuickCartApp.ldb.cartProductsDao()
    }

    suspend fun getAllProductsFromCart(onResult:(List<CartProducts>?) -> Unit) =
        withContext(Dispatchers.IO){
        try {
            val response = cartProductsDao.getAllProductsFromCart()
            onResult(response)
        } catch (e: Exception) {
            onResult(null)
            Log.e("RLTAG", "getAllProductsFromCart: ${e.message}")
        }
    }

    suspend fun clearCart(onResult: (Boolean) -> Unit) = withContext(Dispatchers.IO){
        try {
            val result = cartProductsDao.clearCart()
            onResult(result > 0)
        } catch (e: Exception) {
            onResult(false)
            Log.e("RLTAG", "removeProductFromCart: ${e.message}")
        }
    }

    suspend fun removeProductFromCart(productId: Int, onResult: (Boolean) -> Unit) =
        withContext(Dispatchers.IO){
            try {
                val result = cartProductsDao.removeProductFromCart(productId)
                onResult(result > 0)
            } catch (e: Exception) {
                onResult(false)
                Log.e("RLTAG", "removeProductFromCart: ${e.message}")
            }
        }

    suspend fun modifyQuantity(productId: Int, isSum: Boolean,
                               onResult: (Boolean?) -> Unit) =
        withContext(Dispatchers.IO){
        try {
            val quantity = cartProductsDao.getProductQuantity(productId)
            if (isSum){
                onResult(cartProductsDao.modifyProductQuantity(quantity + 1, productId) > 0)
            } else {
                if (quantity != 0){
                    onResult(cartProductsDao.modifyProductQuantity(quantity - 1, productId) > 0)
                } else {
                    onResult(false)
                }
            }
        } catch (e: Exception){
            onResult(null)
            Log.e("RLTAG", "modifyQuantity: ${e.message}")
        }
    }




}