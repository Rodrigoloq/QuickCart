package com.rodrigoloq.quickcart.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_products")
data class CartProducts(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val product_id: Int,
    val title: String,
    val price: Double,
    val discount_percentage: Double,
    val price_with_discount: Double,
    val thumbnail: String,
    val stock: Int,
    val quantity: Int,
    val sku: String)
