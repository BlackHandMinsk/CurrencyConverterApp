package com.example.currencyconverterapp.domain.model

data class Currency(
    val date: Long,
    var value: Double,
    val base: String,
    var name: String,
    var img: Int,
    var isSelected: Boolean
)