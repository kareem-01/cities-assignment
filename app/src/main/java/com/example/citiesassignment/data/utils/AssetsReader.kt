package com.example.citiesassignment.data.utils

import android.content.Context
import java.io.InputStreamReader

class AssetsReader(private val context: Context) {
    fun readJsonFile(fileName: String): String {
        context.assets.open(fileName).use { inputStream ->
            return InputStreamReader(inputStream).readText()
        }
    }
}