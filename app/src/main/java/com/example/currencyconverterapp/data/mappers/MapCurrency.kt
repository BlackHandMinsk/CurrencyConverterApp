package com.example.currencyconverterapp.data.mappers

import com.example.currencyconverterapp.data.pojo.CurrencyPojo
import com.example.currencyconverterapp.domain.model.Currency
import com.example.currencyconverterapp.domain.util.Mapper

data class MapCurrency(
    val currencyPojoToCurrency: MapCurrencyPojoToCurrency,
)

class MapCurrencyPojoToCurrency : Mapper<CurrencyPojo, Currency> {
    override fun map(from: CurrencyPojo): Currency {
        return Currency(
            date = from.date,
            value = from.value,
            base = from.base,
            name = "",
            img = -1,
            isSelected = false
        )
    }
}