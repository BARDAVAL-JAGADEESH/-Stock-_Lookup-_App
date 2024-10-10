package com.bardaval.stocklookup

// Class representing the entire response from Yahoo Finance API
data class YahooFinanceResponse(
    val quoteResponse: QuoteResponse // Contains the stock data
)

// Class representing the quote response
data class QuoteResponse(
    val result: List<StockData> // List of stock data
)

// Class representing individual stock data
data class StockData(
    val symbol: String, // Stock symbol (e.g., AAPL)
    val longName: String, // Full name of the stock (e.g., Apple Inc.)
    val regularMarketPrice: Double, // Current market price
    val regularMarketChangePercent: Double // Percentage change in market price
)
