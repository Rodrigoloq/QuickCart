package com.rodrigoloq.quickcart.feature.home.model

import android.util.Log
import com.rodrigoloq.quickcart.QuickCartApp
import com.rodrigoloq.quickcart.data.retrofit.ProductService
import com.rodrigoloq.quickcart.data.retrofit.entities.product.Product
import com.rodrigoloq.quickcart.data.retrofit.entities.product.ProductResponse
import com.rodrigoloq.quickcart.data.room.dao.CartProductsDao
import com.rodrigoloq.quickcart.data.room.entities.CartProducts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val productService: ProductService,
    private val cartProductsDao: CartProductsDao
) {


    suspend fun getProductsByCategory(categoryName: String,
                                      onResult:(List<Product>?) -> Unit) =
        withContext(Dispatchers.IO){
        try {
            val response = productService.getProductsByCategory(categoryName)
            onResult(response.products)
        } catch (e: Exception){
            onResult(null)
            Log.e("RLTAG", "getProductsByCategory: ${e.message}")
        }
    }

    suspend fun searchProducts(searchValue: String,
                               onResult:(List<Product>?) -> Unit) =
        withContext(Dispatchers.IO){
            try {
                val response = productService.searchProducts(searchValue)
                onResult(response.products)
            } catch (e: Exception){
                onResult(null)
                Log.e("RLTAG", "getProductsByCategory: ${e.message}")
            }
        }


    suspend fun getCartSize(onResult:(Int) -> Unit){
        try {
            onResult(cartProductsDao.getCartSize())
        } catch (e: Exception) {
            onResult(0)
            Log.e("RLTAG", "getCartSize: ${e.message}")
        }
    }
}