package com.example.currencyconverterapp.data.util

import okio.IOException

sealed class CurrencyRemoteException(message: String): IOException(message) {
    class ClientError(message: String): CurrencyRemoteException(message)
    class ServerError(message: String): CurrencyRemoteException(message)
    class NoNetworkError(message: String): CurrencyRemoteException(message)
    class GenericError(message: String): CurrencyRemoteException(message)
}