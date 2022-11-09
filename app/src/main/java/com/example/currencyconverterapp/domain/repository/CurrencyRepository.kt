package com.example.currencyconverterapp.domain.repository

import com.example.currencyconverterapp.domain.model.Currency
import com.example.currencyconverterapp.domain.util.Record
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    suspend fun getAllCurrencyFromApi(): Flow<Record<List<Currency>>>
}