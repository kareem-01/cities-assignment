package com.example.citiesassignment.data.models


import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("coord")
    val coordinates: Coord,
    @SerializedName("country")
    val country: String,
    @SerializedName("_id")
    val id: Int,
    @SerializedName("name")
    val name: String
) {
    data class Coord(
        @SerializedName("lat")
        val lat: Double,
        @SerializedName("lon")
        val lon: Double
    )
}