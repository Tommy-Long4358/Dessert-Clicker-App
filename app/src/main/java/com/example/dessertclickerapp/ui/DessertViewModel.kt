package com.example.dessertclickerapp.ui

import androidx.lifecycle.ViewModel
import com.example.dessertclickerapp.data.Datasource.dessertList
import com.example.dessertclickerapp.model.Dessert
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DessertViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())

    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    // Constructor to initialize ui state with initial data values
    init {
        // Get first dessert item in list
        val dessertItem = determineDessertToShow(dessertList, 0)

        // Set value of ui state
        _uiState.value = GameUiState(
            currentDessertPrice = dessertItem.price,
            currentDessertImageId = dessertItem.imageId
        )
    }

    /**
     * Determine which dessert to show.
     */
    private fun determineDessertToShow(
        desserts: List<Dessert>,
        dessertsSold: Int
    ): Dessert {
        var dessertToShow = desserts.first()
        for (dessert in desserts) {
            if (dessertsSold >= dessert.startProductionAmount) {
                dessertToShow = dessert
            } else {
                // The list of desserts is sorted by startProductionAmount. As you sell more desserts,
                // you'll start producing more expensive desserts as determined by startProductionAmount
                // We know to break as soon as we see a dessert who's "startProductionAmount" is greater
                // than the amount sold.
                break
            }
        }

        return dessertToShow
    }

    /**
     * Updates the UI state with new data values after each click
     *
     */
    fun onDessertClicked() {
        // Get dessert item to show and change desserts if the number sold exceeds a certain value
        val dessertToShow = determineDessertToShow(dessertList, _uiState.value.dessertsSold.inc())

        // Update UI state with new data values
        _uiState.update { currentState ->
            currentState.copy(
                revenue = currentState.revenue.plus(currentState.currentDessertPrice), // Increase revenue by how much each dessert is
                dessertsSold = currentState.dessertsSold.inc(), // Increase number sold
                currentDessertPrice = dessertToShow.price, // Display price of current dessert being sold
                currentDessertImageId = dessertToShow.imageId // Show image of current dessert
            )
        }
    }
}