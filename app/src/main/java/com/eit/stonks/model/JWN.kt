package com.eit.stonks.model

data class JWN(
    val Change: Double,
    val ChangePercent: Double,
    val ChangePercentYTD: Double,
    val ChangeYTD: Double,
    val High: Double,
    val LastPrice: Double,
    val Low: Double,
    val MSDate: Double,
    val MarketCap: Long,
    val Name: String,
    val Open: Double,
    val Symbol: String,
    val Volume: Int
)