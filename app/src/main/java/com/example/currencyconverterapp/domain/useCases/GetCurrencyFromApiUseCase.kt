package com.example.currencyconverterapp.domain.useCases

import com.example.currencyconverterapp.domain.model.Currency
import com.example.currencyconverterapp.domain.repository.CurrencyRepository
import com.example.currencyconverterapp.domain.util.Record
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrencyFromApiUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    suspend operator fun invoke(): Flow<Record<List<Currency>>> {
        return repository.getAllCurrencyFromApi()
    }
}