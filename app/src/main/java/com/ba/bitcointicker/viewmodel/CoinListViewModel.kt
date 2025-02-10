package com.ba.bitcointicker.viewmodel

import com.ba.bitcointicker.data.model.Coin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ba.bitcointicker.domain.usecase.GetCoinsUseCase
import kotlinx.coroutines.launch

class CoinListViewModel(private val getCoinsUseCase: GetCoinsUseCase): ViewModel() {

    private val _coinList = MutableStateFlow<List<Coin>>(emptyList())
    val coinList: StateFlow<List<Coin>> = _coinList

    fun fetchCoins() {
        viewModelScope.launch {
            _coinList.value = getCoinsUseCase()
        }
    }
}