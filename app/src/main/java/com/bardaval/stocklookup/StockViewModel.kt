package com.bardaval.stocklookup



import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class StockViewModel : ViewModel() {

    val stockData = MutableLiveData<StockData?>()
    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    fun fetchStock(symbol: String) {
        isLoading.value = true
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getStockData(symbol)
                stockData.value = response.quoteResponse.result.firstOrNull()
                errorMessage.value = ""
            } catch (e: Exception) {
                errorMessage.value = "Error: ${e.message}"
            }
            isLoading.value = false
        }
    }
}
