package com.ba.bitcointicker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ba.bitcointicker.domain.usecase.GetCoinsUseCase

class CoinListViewModelFactory(private val getCoinsUseCase: GetCoinsUseCase) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CoinListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CoinListViewModel(getCoinsUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
