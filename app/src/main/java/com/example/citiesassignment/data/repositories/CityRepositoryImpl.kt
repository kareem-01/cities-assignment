package com.example.citiesassignment.data.repositories

import com.example.citiesassignment.data.models.City
import com.example.citiesassignment.data.utils.AssetsReader
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
    private val assetReader: AssetsReader,
    private val gson: Gson
) : CityRepository {
    override suspend fun getAllCities(): List<City> {
        val json = assetReader.readJsonFile("cities.json")
        val cityListType = object : TypeToken<List<City>>() {}
        return gson.fromJson(json, cityListType)
    }
}