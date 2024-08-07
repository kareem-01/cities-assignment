package com.example.citiesassignment.data.repositories

import com.example.citiesassignment.data.models.City

interface CityRepository {
    suspend fun getAllCities():List<City>
}