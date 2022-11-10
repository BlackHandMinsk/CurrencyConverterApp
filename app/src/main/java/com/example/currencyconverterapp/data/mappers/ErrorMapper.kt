package com.example.currencyconverterapp.data.mappers

import android.util.Log
import com.example.currencyconverterapp.data.util.CurrencyRemoteException
import com.example.currencyconverterapp.domain.util.ErrorRecord
import com.example.currencyconverterapp.domain.util.Record
import javax.inject.Inject

class ErrorMapper @Inject constructor() {
    fun mapErrorRecord(e: CurrencyRemoteException): Record.Error {
        Log.e(ErrorMapper::class.simpleName, e.message.toString())
        val errorRecord: ErrorRecord = when (e) {
            is CurrencyRemoteException.ClientError -> ErrorRecord.ClientError
            is CurrencyRemoteException.ServerError -> ErrorRecord.ServerError
            is CurrencyRemoteException.NoNetworkError -> ErrorRecord.NetworkError
            else -> ErrorRecord.GenericError
        }
        return Record.Error(errorRecord)
    }
}