package com.example.dessertclickerapp.ui

data class GameUiState(
    var revenue: Int = 0, // Total revenue made from selling desserts
    var dessertsSold: Int = 0, // Number of desserts sold
    var currentDessertPrice: Int = 0, // Dessert price
    var currentDessertImageId: Int = 0, // Get image ID of dessert from index
)