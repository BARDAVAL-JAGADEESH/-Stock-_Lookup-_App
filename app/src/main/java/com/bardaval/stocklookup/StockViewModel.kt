package com.bardaval.stocklookup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

// ViewModel to handle the logic of fetching stock data
class StockViewModel : ViewModel() {

    // LiveData to store stock data, loading status, and error messages
    val stockData = MutableLiveData<StockData?>()
    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    // Function to fetch stock data using the API
    fun fetchStock(symbol: String) {
        isLoading.value = true // Show loading indicator
        viewModelScope.launch {
            try {
                // Fetch stock data from Yahoo Finance API
                val response = RetrofitClient.instance.getStockData(symbol)
                stockData.value = response.quoteResponse.result.firstOrNull() // Get the first stock result
                errorMessage.value = "" // Clear any previous errors
            } catch (e: Exception) {
                errorMessage.value = "Error: ${e.message}" // Show error message if something goes wrong
            }
            isLoading.value = false // Hide loading indicator
        }
    }
}
