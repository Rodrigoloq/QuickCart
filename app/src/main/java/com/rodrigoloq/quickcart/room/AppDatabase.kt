package com.rodrigoloq.quickcart.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rodrigoloq.quickcart.room.entities.CartProducts
import com.rodrigoloq.quickcart.room.entities.Favorites

@Database(
    entities = [CartProducts::class, Favorites::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartProductsDao(): CartProductsDao
    abstract fun FavoritesDao(): FavoritesDao
}