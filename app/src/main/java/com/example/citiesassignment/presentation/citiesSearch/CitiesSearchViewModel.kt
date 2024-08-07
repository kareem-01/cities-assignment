package com.example.citiesassignment.presentation.citiesSearch

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citiesassignment.cityBinarySearch
import com.example.citiesassignment.data.models.City
import com.example.citiesassignment.domain.GetCitiesUseCase
import com.example.citiesassignment.mergeSort
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class CitiesSearchViewModel @Inject constructor(private val getCitiesUseCase: GetCitiesUseCase) :
    ViewModel(), CitySearchListener {
    private val _state = MutableStateFlow(CitiesSearchUiState())
    val state = _state.asStateFlow()
    private val queryChannel = Channel<String>(Channel.CONFLATED)
    private var previousQuery: String = ""
    private val _effect: MutableSharedFlow<CitiesSearchEffect> = MutableSharedFlow()
    val effect = _effect.asSharedFlow()

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getCitiesUseCase().also { cities ->
                    Log.d("cities_search", cities.toString())
                    updateState { copy(allCities = cities) }
                    state.value.allCities.mergeSort().also { sortedCities ->
                        updateState {
                            copy(
                                searchedCities = sortedCities,
                                allCities = sortedCities,
                                isLoading = false,
                            )
                        }
                    }
                }
            }
            queryChannel
                .consumeAsFlow()
                .debounce(800)
                .collectLatest { query ->
                    updateState { copy(isLoading = true) }
                    search(query)
                }


        }
    }


    private fun search(query: String) {
        if (query.isBlank()) {
            updateState {
                copy(searchedCities = allCities, isLoading = false)
            }
            previousQuery = ""
            return
        }

        val cities = if (query.startsWith(previousQuery)) {
            state.value.searchedCities
        } else {
            state.value.allCities
        }
        val result = cities.cityBinarySearch(query)
        updateState {
            copy(searchedCities = result ?: emptyList(), isLoading = false)
        }
        previousQuery = query
    }

    private fun updateState(reducer: CitiesSearchUiState.() -> CitiesSearchUiState) {
        _state.value = _state.value.reducer()
    }

    override fun onCitySelected(city: City) {
        val geoIntentUri = Uri.parse("geo:${city.coordinates.lat},${city.coordinates.lon}")
        val intent = Intent(Intent.ACTION_VIEW, geoIntentUri)
        try {
            intent.setPackage("com.google.android.apps.maps")

        } catch (_: Exception) {
        }
        sendUiEffect(CitiesSearchEffect.OpenMap(intent))
    }

    override fun onCitySearchQueryChanged(query: String) {
        updateState { copy(searchQuery = query) }
        queryChannel.trySend(query)
    }

    override fun clearSearchQuery() {
        updateState { copy(searchQuery = "") }
        queryChannel.trySend("")
    }

    private fun sendUiEffect(effect: CitiesSearchEffect) {
        viewModelScope.launch { _effect.emit(effect) }
    }
}