package com.bardaval.stocklookup

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Singleton object to create and configure Retrofit instance
object RetrofitClient {
    private const val BASE_URL = "https://query2.finance.yahoo.com/" // Base URL for Yahoo Finance API

    // Lazy initialization of Retrofit and API interface
    val instance: YahooFinanceApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Use Gson for JSON conversion
            .build()
            .create(YahooFinanceApi::class.java)
    }
}
