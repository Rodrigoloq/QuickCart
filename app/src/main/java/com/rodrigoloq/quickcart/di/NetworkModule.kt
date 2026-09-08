package com.rodrigoloq.quickcart.di

import com.rodrigoloq.quickcart.retrofit.ProductService
import com.rodrigoloq.quickcart.retrofit.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideProductService(
        retrofit: Retrofit
    ): ProductService {
        return retrofit.create(ProductService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserService(
        retrofit: Retrofit
    ): UserService {
        return retrofit.create(UserService::class.java)
    }
}