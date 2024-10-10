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

// MainActivity: The main screen where the user can search for stock prices
class MainActivity : AppCompatActivity() {
    // Define variables for all the UI elements
    private lateinit var searchEditText: EditText
    private lateinit var stockPriceTextView: TextView
    private lateinit var stockChangeTextView: TextView
    private lateinit var stockNameTextView: TextView
    private lateinit var searchButton: Button
    private lateinit var loadingProgressBar: ProgressBar
    private lateinit var errorTextView: TextView

    // Create an instance of the ViewModel to handle data logic
    private val stockViewModel: StockViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the UI components
        searchEditText = findViewById(R.id.searchEditText)
        stockPriceTextView = findViewById(R.id.stockPriceTextView)
        stockChangeTextView = findViewById(R.id.stockChangeTextView)
        stockNameTextView = findViewById(R.id.stockNameTextView)
        searchButton = findViewById(R.id.searchButton)
        loadingProgressBar = findViewById(R.id.loadingProgressBar)
        errorTextView = findViewById(R.id.errorTextView)

        // Set up the click event for the search button
        searchButton.setOnClickListener {
            val symbol = searchEditText.text.toString().trim() // Get the entered stock symbol
            if (symbol.isNotEmpty()) {
                stockViewModel.fetchStock(symbol) // Fetch stock data
            }
        }

        // Observe the stock data from the ViewModel
        stockViewModel.stockData.observe(this, Observer { stock ->
            if (stock != null) {
                // Display stock data if available
                stockNameTextView.text = stock.longName
                stockPriceTextView.text = "Price: $${stock.regularMarketPrice}"
                stockChangeTextView.text = "Change: ${stock.regularMarketChangePercent}%"
                showStockInfo(true) // Show stock info on screen
            } else {
                // Show error message if no stock data is found
                showStockInfo(false)
                errorTextView.text = "No data found."
            }
        })

        // Observe the loading status to show or hide the progress bar
        stockViewModel.isLoading.observe(this, Observer { isLoading ->
            loadingProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        // Observe any error messages and display them if necessary
        stockViewModel.errorMessage.observe(this, Observer { error ->
            errorTextView.text = error
            errorTextView.visibility = if (error.isEmpty()) View.GONE else View.VISIBLE
        })
    }

    // Function to show or hide stock information based on the data availability
    private fun showStockInfo(show: Boolean) {
        stockNameTextView.visibility = if (show) View.VISIBLE else View.GONE
        stockPriceTextView.visibility = if (show) View.VISIBLE else View.GONE
        stockChangeTextView.visibility = if (show) View.VISIBLE else View.GONE
    }
}
