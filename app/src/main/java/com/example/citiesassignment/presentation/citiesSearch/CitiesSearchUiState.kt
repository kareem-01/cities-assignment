package com.example.citiesassignment.presentation.citiesSearch

import com.example.citiesassignment.data.models.City

data class CitiesSearchUiState(
    val isLoading: Boolean = true,
    val searchQuery: String = "",
    val searchedItems: List<City> = emptyList(),
    val allCities: List<City> = emptyList(),

)
