package com.example.currencyconverterapp.presentation.fragments.adapters

import com.example.currencyconverterapp.domain.model.Currency

interface AdaptersListener {
    fun onClickItem(currency: Currency)
    fun onSetNumber(number: Double)
}