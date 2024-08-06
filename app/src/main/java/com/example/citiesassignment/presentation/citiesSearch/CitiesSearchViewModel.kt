package com.example.citiesassignment.presentation.citiesSearch

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citiesassignment.data.models.City
import com.example.citiesassignment.domain.GetCitiesUseCase
import com.example.citiesassignment.mergeSort
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class CitiesSearchViewModel @Inject constructor(private val getCitiesUseCase: GetCitiesUseCase) :
    ViewModel() {
    private val _state = MutableStateFlow(CitiesSearchUiState())
    val state = _state.asStateFlow()
    private val queryChannel = Channel<String>(Channel.CONFLATED)
    private var sortedCities: List<City>? = null

    init {
        viewModelScope.launch {
            getCitiesUseCase().also { cities ->
                updateState { copy(allCities = cities) }
                Log.d("sorted_cities", "before")
                val timeBefore = System.currentTimeMillis()
                sortedCities = cities.mergeSort()
//                val timeAfter = System.currentTimeMillis()
//                Log.d("sorted_cities", sortedCities.toString())
//                Log.d("sorted_cities", (timeAfter - timeBefore).toString())

            }
            queryChannel
                .consumeAsFlow()
                .debounce(800)
                .collectLatest { query ->
                    if (query.isNotEmpty()) {
                        updateState { copy(isLoading = true) }
                        search(query)
                    }
                }
        }
    }

    fun updateSearchQuery(query: String) {
        updateState { copy(searchQuery = query) }
        queryChannel.trySend(query)
    }

    private fun search(query: String) {

    }

    private fun updateState(reducer: CitiesSearchUiState.() -> CitiesSearchUiState) {
        _state.value = _state.value.reducer()
    }
}