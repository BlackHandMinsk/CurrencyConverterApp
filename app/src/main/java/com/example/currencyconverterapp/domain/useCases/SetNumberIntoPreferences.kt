package com.example.currencyconverterapp.domain.useCases

import com.example.currencyconverterapp.domain.repository.CurrencyRepository
import javax.inject.Inject

class SetNumberIntoPreferences @Inject constructor(
    private val repository: CurrencyRepository
) {
    suspend operator fun invoke(number: Double) {
        return repository.setNumber(number)
    }
}