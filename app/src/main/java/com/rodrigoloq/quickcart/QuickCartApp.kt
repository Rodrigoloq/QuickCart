package com.rodrigoloq.quickcart

import android.app.Application
import androidx.room.Room
import com.rodrigoloq.quickcart.room.AppDatabase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuickCartApp : Application() {
    companion object{
        lateinit var rdb: Retrofit
        lateinit var ldb: AppDatabase
    }

    private fun retrofitBuilder(baseUrl: String): Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        rdb = retrofitBuilder("https://dummyjson.com")
        ldb = Room.databaseBuilder(this,
            AppDatabase::class.java,
            "quickcart_db").build()
    }
}