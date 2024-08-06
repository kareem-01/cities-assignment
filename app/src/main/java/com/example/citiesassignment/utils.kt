package com.example.citiesassignment

import com.example.citiesassignment.data.models.City

fun List<City>.mergeSort(cities: MutableList<City> = this.toMutableList()): MutableList<City> {
    if (cities.size <= 1) {
        return cities
    }
    val middle = cities.size / 2
    val left = cities.subList(0, middle)
    val right = cities.subList(middle, cities.size)

    return merge(mergeSort(left), mergeSort(right))
}

fun merge(left: MutableList<City>, right: MutableList<City>): MutableList<City> {
    var indexLeft = 0
    var indexRight = 0
    val newList: MutableList<City> = mutableListOf()

    while (indexLeft < left.size && indexRight < right.size) {
        if (left[indexLeft].name.compareTo(right[indexRight].name) <= 0) {
            newList.add(left[indexLeft])
            indexLeft++
        } else {
            newList.add(right[indexRight])
            indexRight++
        }
    }

    while (indexLeft < left.size) {
        newList.add(left[indexLeft])
        indexLeft++
    }

    while (indexRight < right.size) {
        newList.add(right[indexRight])
        indexRight++
    }

    return newList
}