package com.ba.bitcointicker.data.remote

import com.ba.bitcointicker.data.model.Coin
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinApiService {
    @GET("coins/markets")
    suspend fun getCoins(
        @Query("vs_currency") currency: String = "usd"
    ): List<Coin>
}