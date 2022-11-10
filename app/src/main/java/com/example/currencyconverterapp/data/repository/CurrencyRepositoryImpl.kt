package com.example.currencyconverterapp.data.repository

import com.example.currencyconverterapp.data.CurrencyApiService
import com.example.currencyconverterapp.data.mappers.ErrorMapper
import com.example.currencyconverterapp.data.mappers.MapCurrency
import com.example.currencyconverterapp.data.util.CurrencyRemoteException
import com.example.currencyconverterapp.data.util.ResourceManager
import com.example.currencyconverterapp.domain.model.Currency
import com.example.currencyconverterapp.domain.repository.CurrencyRepository
import com.example.currencyconverterapp.domain.util.Record
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CurrencyRepositoryImpl(
    private val api: CurrencyApiService,
    private val mapError: ErrorMapper,
    private val resourceManager: ResourceManager,
    private val mapCurrency: MapCurrency

) : CurrencyRepository {
    override suspend fun getAllCurrencyFromApi(): Flow<Record<List<Currency>>> = flow {
        emit(Record.Loading)
        try {
            api.getAllCurrencies()
                .run {
                    emit(Record.Success(mapCurrency
                        .currencyPojoToCurrency
                        .mapList(this)
                        .map {
                            it.apply {
                                name = resourceManager.getStringResourceId(this.base)
                                img = resourceManager.getDrawableId(this.base)
                            }
                        }
                    ))
                }
        } catch (e: CurrencyRemoteException) {
            emit(mapError.mapErrorRecord(e))
        }
    }
}