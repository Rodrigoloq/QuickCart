package com.rodrigoloq.quickcart.feature.categories.model

import android.util.Log
import com.rodrigoloq.quickcart.QuickCartApp
import com.rodrigoloq.quickcart.data.retrofit.ProductService
import com.rodrigoloq.quickcart.data.retrofit.entities.product.Product
import com.rodrigoloq.quickcart.data.room.dao.CartProductsDao
import com.rodrigoloq.quickcart.data.room.entities.CartProducts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoriesRepository @Inject constructor(
    private val productService: ProductService,
    private val cartProductsDao: CartProductsDao
){


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

    suspend fun addProductToCart(product: CartProducts,
                                 onResult:(Boolean) -> Unit) =
        withContext(Dispatchers.IO){
            try {
                val exists = cartProductsDao.doesProductExists(product.product_id)
                if (exists){
                    val quantity = cartProductsDao.getProductQuantity(product.product_id)
                    cartProductsDao.modifyProductQuantity(quantity + 1, product.product_id)
                    onResult(true)
                } else {
                    cartProductsDao.addProductToCart(product)
                    onResult(true)
                }
            } catch (e: Exception) {
                onResult(false)
                Log.e("RLTAG", "addProductToCart: ${e.message}")
            }
        }
}