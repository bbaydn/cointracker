package com.ba.bitcointicker.data.remote

import com.ba.bitcointicker.data.model.Coin
import com.ba.bitcointicker.data.model.CoinDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinApiService {
    @GET("coins/markets")
    suspend fun getCoins(
        @Query("vs_currency") currency: String = "usd"
    ): List<Coin>

    @GET("coins/{id}")
    suspend fun getCoinDetail(@Path("id") coinId: String): CoinDetail
}