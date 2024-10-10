package com.bardaval.stocklookup

import retrofit2.http.GET
import retrofit2.http.Query

// Interface to define API calls to Yahoo Finance
interface YahooFinanceApi {
    // Define the API endpoint for fetching stock data
    @GET("v6/finance/quote")
    suspend fun getStockData(
        @Query("symbols") symbols: String // API expects a stock symbol
    ): YahooFinanceResponse
}
