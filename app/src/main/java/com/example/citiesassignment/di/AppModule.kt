package com.example.citiesassignment.di

import android.content.Context
import com.example.citiesassignment.data.repositories.CityRepository
import com.example.citiesassignment.data.repositories.CityRepositoryImpl
import com.example.citiesassignment.data.utils.AssetsReader
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideAssetReader(@ApplicationContext context: Context): AssetsReader {
        return AssetsReader(context)
    }

    @Singleton
    @Provides
    fun provideGson(): Gson = Gson()

    @Singleton
    @Provides
    fun provideCityRepository(
        assetReader: AssetsReader,
        gson: Gson
    ): CityRepository {
        return CityRepositoryImpl(assetReader, gson)
    }
}