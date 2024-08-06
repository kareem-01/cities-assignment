package com.example.citiesassignment.presentation.citiesSearch

interface CitySearchListener {
    fun onCitySelected(city: String)
    fun onCitySearchQueryChanged(query: String)
    fun clearSearchQuery()
}