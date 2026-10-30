package com.rodrigoloq.quickcart.di

import android.content.Context
import androidx.room.Room
import com.rodrigoloq.quickcart.data.room.AppDatabase
import com.rodrigoloq.quickcart.data.room.dao.CartProductsDao
import com.rodrigoloq.quickcart.data.room.dao.FavoritesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "quickcart_db"
        ).build()
    }

    @Provides
    fun provideCartProductsDao(
        database: AppDatabase
    ): CartProductsDao {
        return database.cartProductsDao()
    }

    @Provides
    fun provideFavoritesDao(
        database: AppDatabase
    ): FavoritesDao {
        return database.FavoritesDao()
    }
}