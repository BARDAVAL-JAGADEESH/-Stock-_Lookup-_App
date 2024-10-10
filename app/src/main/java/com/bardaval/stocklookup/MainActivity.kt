package com.bardaval.stocklookup

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {
    private lateinit var searchEditText: EditText
    private lateinit var stockPriceTextView: TextView
    private lateinit var stockChangeTextView: TextView
    private lateinit var stockNameTextView: TextView
    private lateinit var searchButton: Button
    private lateinit var loadingProgressBar: ProgressBar
    private lateinit var errorTextView: TextView

    private val stockViewModel: StockViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        searchEditText = findViewById(R.id.searchEditText)
        stockPriceTextView = findViewById(R.id.stockPriceTextView)
        stockChangeTextView = findViewById(R.id.stockChangeTextView)
        stockNameTextView = findViewById(R.id.stockNameTextView)
        searchButton = findViewById(R.id.searchButton)
        loadingProgressBar = findViewById(R.id.loadingProgressBar)
        errorTextView = findViewById(R.id.errorTextView)

        searchButton.setOnClickListener {
            val symbol = searchEditText.text.toString().trim()
            if (symbol.isNotEmpty()) {
                stockViewModel.fetchStock(symbol)
            }
        }

        stockViewModel.stockData.observe(this, Observer { stock ->
            if (stock != null) {
                stockNameTextView.text = stock.longName
                stockPriceTextView.text = "Price: $${stock.regularMarketPrice}"
                stockChangeTextView.text = "Change: ${stock.regularMarketChangePercent}%"
                showStockInfo(true)
            } else {
                showStockInfo(false)
                errorTextView.text = "No data found."
            }
        })

        stockViewModel.isLoading.observe(this, Observer { isLoading ->
            loadingProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        stockViewModel.errorMessage.observe(this, Observer { error ->
            errorTextView.text = error
            errorTextView.visibility = if (error.isEmpty()) View.GONE else View.VISIBLE
        })
    }

    private fun showStockInfo(show: Boolean) {
        stockNameTextView.visibility = if (show) View.VISIBLE else View.GONE
        stockPriceTextView.visibility = if (show) View.VISIBLE else View.GONE
        stockChangeTextView.visibility = if (show) View.VISIBLE else View.GONE
    }
}
