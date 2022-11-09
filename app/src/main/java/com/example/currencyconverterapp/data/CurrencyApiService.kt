package com.example.currencyconverterapp.data

import com.example.currencyconverterapp.data.pojo.CurrencyPojo
import retrofit2.http.GET
import retrofit2.http.Path


interface CurrencyApiService {

    @GET("/myWebsiteBackend/api/currency/{base}")
    suspend fun getCurrency(
        @Path("base") base: String,
    ): CurrencyPojo

    @GET("/myWebsiteBackend/api/currency")
    suspend fun getAllCurrencies(): List<CurrencyPojo>
}