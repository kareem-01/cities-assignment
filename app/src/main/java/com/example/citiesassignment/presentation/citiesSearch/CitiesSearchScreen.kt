package com.example.citiesassignment.presentation.citiesSearch

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CitiesSearchScreen(viewModel: CitiesSearchViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState()
    CitiesSearchContent()
}

@Composable
private fun CitiesSearchContent() {

}
