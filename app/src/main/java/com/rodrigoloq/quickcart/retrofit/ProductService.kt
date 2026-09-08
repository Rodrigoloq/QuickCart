package com.rodrigoloq.quickcart.retrofit

import com.rodrigoloq.quickcart.retrofit.entities.product.Product
import com.rodrigoloq.quickcart.retrofit.entities.product.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductService {

    @GET("/products")
    suspend fun getAllProducts(): ProductResponse

    @GET("/products/{productId}")
    suspend fun getProductsById(@Path("productId") productId: String): Product

    @GET("/products/category/{category}")
    suspend fun getProductsByCategory(@Path("category") categoryName: String): ProductResponse

    @GET("products/search")
    suspend fun searchProducts(@Query("q") searchValue: String): ProductResponse
}