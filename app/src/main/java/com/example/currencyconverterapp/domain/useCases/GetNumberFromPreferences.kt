package com.example.currencyconverterapp.domain.useCases

import com.example.currencyconverterapp.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNumberFromPreferences @Inject constructor(
    private val repository: CurrencyRepository
) {
    operator fun invoke(): Flow<Double> {
        return repository.getNumber()
    }
}