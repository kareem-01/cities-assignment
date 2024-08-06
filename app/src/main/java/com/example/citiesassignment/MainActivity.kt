package com.example.citiesassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.citiesassignment.presentation.citiesSearch.CitiesSearchScreen
import com.example.citiesassignment.ui.theme.CitiesAssignmentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CitiesAssignmentTheme {
                CitiesSearchScreen()
            }
        }
    }
}
