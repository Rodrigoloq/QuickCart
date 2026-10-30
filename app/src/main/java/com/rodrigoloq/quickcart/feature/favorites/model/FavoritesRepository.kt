package com.rodrigoloq.quickcart.feature.favorites.model

import android.util.Log
import com.rodrigoloq.quickcart.QuickCartApp
import com.rodrigoloq.quickcart.data.room.dao.CartProductsDao
import com.rodrigoloq.quickcart.data.room.dao.FavoritesDao
import com.rodrigoloq.quickcart.data.room.entities.CartProducts
import com.rodrigoloq.quickcart.data.room.entities.Favorites
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class FavoritesRepository @Inject constructor(
    private val cartProductsDao: CartProductsDao,
    private val favoritesDao: FavoritesDao
) {

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