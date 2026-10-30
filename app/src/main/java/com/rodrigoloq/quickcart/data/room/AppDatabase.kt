package com.rodrigoloq.quickcart.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rodrigoloq.quickcart.data.room.dao.CartProductsDao
import com.rodrigoloq.quickcart.data.room.dao.FavoritesDao
import com.rodrigoloq.quickcart.data.room.entities.CartProducts
import com.rodrigoloq.quickcart.data.room.entities.Favorites

@Database(
    entities = [CartProducts::class, Favorites::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartProductsDao(): CartProductsDao
    abstract fun FavoritesDao(): FavoritesDao
}