package com.rodrigoloq.quickcart.favorites.model

import android.util.Log
import com.rodrigoloq.quickcart.QuickCartApp
import com.rodrigoloq.quickcart.room.CartProductsDao
import com.rodrigoloq.quickcart.room.FavoritesDao
import com.rodrigoloq.quickcart.room.entities.CartProducts
import com.rodrigoloq.quickcart.room.entities.Favorites
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class FavoritesRepository {
    private val favoritesDao: FavoritesDao by lazy {
        QuickCartApp.ldb.FavoritesDao()
    }

    private val cartProductsDao: CartProductsDao by lazy {
        QuickCartApp.ldb.cartProductsDao()
    }

    suspend fun getAllFavorites(onResult:(List<Favorites>?) -> Unit) =
        withContext(Dispatchers.IO){
        try {
            onResult(favoritesDao.getAllFavorites())
        } catch (e: Exception) {
            onResult(null)
            Log.e("RLTAG", "getAllFavorites: ${e.message}")
        }
    }

    suspend fun removeFavorite(productId: Int,
                               onResult:(Boolean) -> Unit) =
        withContext(Dispatchers.IO){
        try {
            onResult(favoritesDao.removeFavorite(productId) > 0)
        } catch (e: Exception) {
            onResult(false)
            Log.e("RLTAG", "removeFavorite: ${e.message}")
        }
    }

    suspend fun clearFavorites(onResult: (Boolean) -> Unit) =
        withContext(Dispatchers.IO){
            try {
                onResult(favoritesDao.clearFavorites() > 0)
            } catch (e: Exception) {
                onResult(false)
                Log.e("RLTAG", "clearFavorites: ${e.message}")
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