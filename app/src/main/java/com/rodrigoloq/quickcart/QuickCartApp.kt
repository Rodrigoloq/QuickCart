package com.rodrigoloq.quickcart

import android.app.Application
import androidx.room.Room
import com.rodrigoloq.quickcart.data.room.AppDatabase
import dagger.hilt.android.HiltAndroidApp
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@HiltAndroidApp
class QuickCartApp : Application()