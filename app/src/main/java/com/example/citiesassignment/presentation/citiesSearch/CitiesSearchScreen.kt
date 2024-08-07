package com.example.citiesassignment.presentation.citiesSearch

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.citiesassignment.R
import com.example.citiesassignment.noRippleClick
import com.example.citiesassignment.presentation.components.CityCard
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CitiesSearchScreen(viewModel: CitiesSearchViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    CitiesSearchContent(state, viewModel)
    LaunchedEffect(key1 = state.isLoading) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is CitiesSearchEffect.OpenMap -> {
                    startActivity(context, effect.intent, null)
                }
            }
        }
    }
}


@Composable
private fun CitiesSearchContent(state: CitiesSearchUiState, listener: CitySearchListener) {
    val interactionSource = remember { MutableInteractionSource() }
    val focused by interactionSource.collectIsFocusedAsState()

    Column(
        modifier = Modifier
            .systemBarsPadding()
            .padding(top = 8.dp, start = 16.dp, end = 16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),

        ) {
        TextField(
            value = state.searchQuery,
            onValueChange = listener::onCitySearchQueryChanged,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                cursorColor = if (focused) Color.Black else Color.LightGray,
                focusedTextColor = Color.Black
            ),
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .border(
                    1.dp,
                    color = if (focused) Color.Black else Color.LightGray,
                    RoundedCornerShape(12.dp)
                ),
            trailingIcon = {
                if (state.searchQuery.isNotEmpty())
                    Icon(
                        painter = painterResource(id = R.drawable.exit),
                        contentDescription = null,
                        modifier = Modifier.noRippleClick { listener.clearSearchQuery() },
                        tint = Color.Black
                    )
            },
            interactionSource = interactionSource
        )
        if (state.isLoading)
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            } else
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                items(state.searchedCities.size) { index ->
                    CityCard(
                        city = state.searchedCities[index],
                        onCityClick = listener::onCitySelected
                    )
                }

                if (state.searchedCities.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No cities found \n try searching for a different name",
                                style = MaterialTheme.typography.titleMedium,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

            }

    }

}
