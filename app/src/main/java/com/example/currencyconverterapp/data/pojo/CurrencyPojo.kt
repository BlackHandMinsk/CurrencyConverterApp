package com.example.currencyconverterapp.data.pojo

import com.squareup.moshi.Json

data class CurrencyPojo(
    @Json(name = "date") val date: Long,
    @Json(name = "rate") val value: Double,
    @Json(name = "base") val base: String
)