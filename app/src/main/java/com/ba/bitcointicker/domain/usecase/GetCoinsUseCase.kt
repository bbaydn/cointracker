package com.ba.bitcointicker.domain.usecase

import com.ba.bitcointicker.data.model.Coin
import com.ba.bitcointicker.data.repository.CoinRepository

class GetCoinsUseCase(private val repository: CoinRepository) {
    suspend operator fun invoke(): List<Coin> {
        return repository.getCoins()
    }
}