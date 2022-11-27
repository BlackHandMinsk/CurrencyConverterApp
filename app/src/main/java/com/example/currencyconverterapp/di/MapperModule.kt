package com.example.currencyconverterapp.di

import com.example.currencyconverterapp.data.mappers.MapCurrency
import com.example.currencyconverterapp.data.mappers.MapCurrencyPojoToCurrency
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    @Singleton
    fun provideMapRoute(
        currencyPojoToCurrency: MapCurrencyPojoToCurrency,
    ): MapCurrency {
        return MapCurrency(
            currencyPojoToCurrency
        )
    }

    @Provides
    fun provideMapCurrencyPojoToCurrency(): MapCurrencyPojoToCurrency {
        return MapCurrencyPojoToCurrency()
    }

}