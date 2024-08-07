package com.example.citiesassignment.presentation.citiesSearch

import android.content.Intent

sealed interface CitiesSearchEffect {
    data class OpenMap(val intent: Intent) : CitiesSearchEffect
}
