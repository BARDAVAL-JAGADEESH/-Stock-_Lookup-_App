package com.bardaval.stocklookup

data class YahooFinanceResponse(
    val quoteResponse: QuoteResponse
)

data class QuoteResponse(
    val result: List<StockData>
)

data class StockData(
    val symbol: String,
    val longName: String,
    val regularMarketPrice: Double,
    val regularMarketChangePercent: Double
)
