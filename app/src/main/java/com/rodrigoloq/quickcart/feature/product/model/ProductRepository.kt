package com.rodrigoloq.quickcart.feature.product.model

import android.util.Log
import com.rodrigoloq.quickcart.QuickCartApp
import com.rodrigoloq.quickcart.data.retrofit.ProductService
import com.rodrigoloq.quickcart.data.retrofit.entities.product.Product
import com.rodrigoloq.quickcart.data.room.dao.CartProductsDao
import com.rodrigoloq.quickcart.data.room.dao.FavoritesDao
import com.rodrigoloq.quickcart.data.room.entities.CartProducts
import com.rodrigoloq.quickcart.data.room.entities.Favorites
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productService: ProductService,
    private val favoritesDao: FavoritesDao,
    private val cartProductsDao: CartProductsDao
) {
    suspend fun getProductDetail(productId: String,
                                 onResult:(Product?) -> Unit) =
        withContext(Dispatchers.IO){
        try {
            val result = productService.getProductsById(productId)
            onResult(result)
        } catch (e: Exception) {
            onResult(null)
            Log.e("RLTAG", "getProductDetail: ${e.message}", )
        }
    }

    suspend fun addProductToFavorites(favorite: Favorites,
                                      onResult:(Boolean) -> Unit) =
        withContext(Dispatchers.IO){
            try {
                if(favoritesDao.isProductFavorite(favorite.product_id)){
                    onResult(false)
                } else {
                    onResult(favoritesDao.addFavorite(favorite) > 0)
                }
            } catch (e: Exception) {
                onResult(false)
                Log.e("RLTAG", "addProductToFavorites: ${e.message}")
            }
        }

    suspend fun removeFavorite(productId: Int,
                               onResult:(Boolean) -> Unit)=
        withContext(Dispatchers.IO){
            try {
                onResult(favoritesDao.removeFavorite(productId) > 0)
            } catch (e: Exception) {
                onResult(false)
                Log.e("RLTAG", "removeFavorite: ${e.message}")
            }
        }

    suspend fun isProductFavorite(productId: Int,
                                  onResult: (Boolean) -> Unit) =
        withContext(Dispatchers.IO){
            try {
                onResult(favoritesDao.isProductFavorite(productId))
            } catch (e: Exception) {
                onResult(false)
                Log.e("RLTAG", "isProductFavorite: ${e.message}")
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