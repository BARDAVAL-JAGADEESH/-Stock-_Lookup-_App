package com.bardaval.stocklookup

import retrofit2.http.GET
import retrofit2.http.Query

interface YahooFinanceApi {
    @GET("v6/finance/quote")
    suspend fun getStockData(
        @Query("symbols") symbols: String
    ): YahooFinanceResponse
}
