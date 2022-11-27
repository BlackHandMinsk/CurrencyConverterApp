package com.example.currencyconverterapp.di

import android.content.Context
import com.example.currencyconverterapp.data.CurrencyApiService
import com.example.currencyconverterapp.data.mappers.ErrorMapper
import com.example.currencyconverterapp.data.mappers.MapCurrency
import com.example.currencyconverterapp.data.preferences.PreferenceManager
import com.example.currencyconverterapp.data.repository.CurrencyRepositoryImpl
import com.example.currencyconverterapp.data.util.ResourceManager
import com.example.currencyconverterapp.domain.repository.CurrencyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideResourceManager(
        @ApplicationContext context: Context
    ): ResourceManager {
        return ResourceManager(context)
    }

    @Provides
    @Singleton
    fun provideCurrencyRepository(
        api: CurrencyApiService,
        mapError: ErrorMapper,
        resourceManager: ResourceManager,
        mapCurrency: MapCurrency,
        preferenceManager: PreferenceManager
    ): CurrencyRepository {
        return CurrencyRepositoryImpl(
            api = api,
            mapError = mapError,
            resourceManager = resourceManager,
            mapCurrency = mapCurrency,
            preferenceManager = preferenceManager
        )
    }
}