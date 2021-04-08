package com.eit.stonks.model

data class BRKA(
    val Change: Int,
    val ChangePercent: Double,
    val ChangePercentYTD: Double,
    val ChangeYTD: Int,
    val High: Int,
    val LastPrice: Int,
    val Low: Int,
    val MSDate: Double,
    val MarketCap: Long,
    val Name: String,
    val Open: Double,
    val Symbol: String,
    val Volume: Int
)