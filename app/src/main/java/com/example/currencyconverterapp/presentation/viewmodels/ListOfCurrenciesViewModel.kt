package com.example.currencyconverterapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverterapp.domain.model.Currency
import com.example.currencyconverterapp.domain.useCases.GetCurrencyFromApiUseCase
import com.example.currencyconverterapp.domain.useCases.GetNumberFromPreferences
import com.example.currencyconverterapp.domain.util.Record
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TIME_UPDATES = 5000L

@HiltViewModel
class ListOfCurrenciesViewModel @Inject constructor(
    private val getCurrency: GetCurrencyFromApiUseCase,
    private val getNumber: GetNumberFromPreferences
) : ViewModel() {

    private var updateJob: Job? = null
    private var getNumberJob: Job? = null

    private val selectedCurrency = MutableStateFlow<Currency?>(null)

    private val number = MutableStateFlow(1.0)

    private var _response = MutableStateFlow<Record<List<Currency>>>(Record.Empty)
    val response = combine(_response, selectedCurrency, number) { response, sCurrency, num ->
        when (response) {
            is Record.Success -> Record.Success(
                sCurrency?.let { sC ->
                    val actualList = response.data
                        .filter { it.img != 0 }
                        .map { cr ->
                            cr.apply {
                                this.value *= num
                            }
                        }
                        .toMutableList()
                    actualList.removeIf { it.name == sC.name }
                    actualList.add(0, sC.apply {
                        this.value = num
                        this.isSelected = true
                    })
                    actualList.toList()
                } ?: response.data.filter { it.img != 0 }
            )
            else -> response
        }
    }

    init {
        getCurrency()
        getNumber()
    }

    private fun getNumber() {
        getNumberJob?.cancel()
        getNumberJob =
            viewModelScope.launch { getNumber.invoke().collect { number.tryEmit(it) } }
    }

    private fun getCurrency() {
        updateJob?.cancel()
        updateJob = viewModelScope.launch {
            while (true) {
                setResponse()
                delay(TIME_UPDATES)
            }
        }
    }

    private suspend fun setResponse() {
        getCurrency.invoke().onEach { _response.tryEmit(it) }.collect()
    }

    fun setCounter(currency: Currency) {
        selectedCurrency.tryEmit(currency)
    }
}