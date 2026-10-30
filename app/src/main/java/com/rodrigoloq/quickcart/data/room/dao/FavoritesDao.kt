package com.rodrigoloq.quickcart.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rodrigoloq.quickcart.data.room.entities.Favorites

@Dao
interface FavoritesDao {
    @Insert
    suspend fun addFavorite(favorite: Favorites): Long

    @Query("SELECT * FROM favorites")
    suspend fun getAllFavorites(): List<Favorites>

    @Query("DELETE FROM favorites WHERE product_id = :productId")
    suspend fun removeFavorite(productId: Int): Int

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE product_id = :productId)")
    suspend fun isProductFavorite(productId: Int): Boolean

    @Query("DELETE FROM favorites")
    suspend fun clearFavorites(): Int
}