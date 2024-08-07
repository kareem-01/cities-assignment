package com.example.citiesassignment.domain

import com.example.citiesassignment.data.models.City
import com.example.citiesassignment.data.repositories.CityRepository
import javax.inject.Inject

class GetCitiesUseCase @Inject constructor(private val cityRepository: CityRepository) {
    suspend operator fun invoke(): List<City> = cityRepository.getAllCities()
}