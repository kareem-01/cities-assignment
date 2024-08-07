package com.example.citiesassignment.presentation.citiesSearch

import com.example.citiesassignment.data.models.City

interface CitySearchListener {
    fun onCitySelected(city: City)
    fun onCitySearchQueryChanged(query: String)
    fun clearSearchQuery()
}