package com.example.citiesassignment

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.Modifier
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

private fun merge(left: MutableList<City>, right: MutableList<City>): MutableList<City> {
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

fun Modifier.noRippleClick(onClick: () -> Unit): Modifier {
    val interaction = MutableInteractionSource()
    return this.then(
        Modifier.clickable(
            interactionSource = interaction,
            indication = null,
            onClick = onClick
        )
    )
}

fun List<City>.cityBinarySearch( target: String): List<City>? {
    val cities: List<City> = this
    if (cities.isEmpty()) return null

    var low = 0
    var high = cities.size - 1
    val result = mutableListOf<City>()

    while (low <= high) {
        val mid = (low + high) / 2
        val midCity = cities[mid]

        when {
            midCity.name.startsWith(target, ignoreCase = false) -> {
                // Add the midCity to the result list
                result.add(midCity)

                // Search for other cities starting with the target in the left half
                var leftIndex = mid - 1
                while (leftIndex >= 0 && cities[leftIndex].name.startsWith(target, ignoreCase = true)) {
                    result.add(cities[leftIndex])
                    leftIndex--
                }

                // Search for other cities starting with the target in the right half
                var rightIndex = mid + 1
                while (rightIndex < cities.size && cities[rightIndex].name.startsWith(target, ignoreCase = true)) {
                    result.add(cities[rightIndex])
                    rightIndex++
                }

                return result
            }
            midCity.name.compareTo(target, ignoreCase = true) < 0 -> low = mid + 1
            else -> high = mid - 1
        }
    }
    return null
}