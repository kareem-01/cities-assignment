package com.example.citiesassignment.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.citiesassignment.data.models.City

@Composable
fun CityCard(city: City, onCityClick: (City) -> Unit) {
    Card(modifier = Modifier.fillMaxWidth() .clickable { onCityClick(city) }, elevation = CardDefaults.cardElevation()) {
        Text(
            text = city.name + ", " + city.country,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(start = 16.dp)
        )
        Text(
            text = "coordinates:  lat:${city.coordinates.lat}, long:${city.coordinates.lon}",
            modifier = Modifier
                .padding(top = 8.dp, start = 16.dp)
                .alpha(.7f),
            style = MaterialTheme.typography.titleSmall
        )
    }
}