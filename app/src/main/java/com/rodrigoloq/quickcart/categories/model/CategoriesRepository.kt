package com.rodrigoloq.quickcart.categories.model

import android.util.Log
import com.rodrigoloq.quickcart.QuickCartApp
import com.rodrigoloq.quickcart.retrofit.ProductService
import com.rodrigoloq.quickcart.retrofit.entities.product.Product
import com.rodrigoloq.quickcart.room.CartProductsDao
import com.rodrigoloq.quickcart.room.entities.CartProducts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoriesRepository {
    private val productService: ProductService by lazy {
        QuickCartApp.rdb.create(ProductService::class.java) }

    private val cartProductsDao: CartProductsDao by lazy {
        QuickCartApp.ldb.cartProductsDao()
    }

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