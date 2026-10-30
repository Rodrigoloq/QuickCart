package com.rodrigoloq.quickcart.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rodrigoloq.quickcart.data.room.entities.CartProducts

@Dao
interface CartProductsDao {

    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    suspend fun addProductToCart(product: CartProducts): Long

    @Query("SELECT * FROM cart_products")
    suspend fun getAllProductsFromCart(): List<CartProducts>

    @Query("SELECT quantity FROM cart_products WHERE product_id = :productId")
    suspend fun getProductQuantity(productId: Int): Int

    @Query("UPDATE cart_products SET quantity = :quantity WHERE product_id = :productId")
    suspend fun modifyProductQuantity(quantity: Int, productId: Int): Int

    @Query("SELECT EXISTS(SELECT 1 FROM cart_products WHERE product_id = :productId)")
    suspend fun doesProductExists(productId: Int): Boolean

    @Query("DELETE FROM cart_products WHERE product_id = :productId")
    suspend fun removeProductFromCart(productId: Int): Int

    @Query("DELETE FROM cart_products")
    suspend fun clearCart(): Int

    @Query("SELECT COUNT(*) FROM cart_products")
    suspend fun getCartSize(): Int

}