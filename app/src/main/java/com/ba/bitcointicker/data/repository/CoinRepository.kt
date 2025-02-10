package com.ba.bitcointicker.data.repository

import com.ba.bitcointicker.data.model.Coin
import com.ba.bitcointicker.data.model.CoinDetail
import com.ba.bitcointicker.data.remote.CoinApiService

class CoinRepository(private val api: CoinApiService) {
    suspend fun getCoins(): List<Coin> = api.getCoins()

    suspend fun getCoinDetail(coinId: String): CoinDetail = api.getCoinDetail(coinId)

}