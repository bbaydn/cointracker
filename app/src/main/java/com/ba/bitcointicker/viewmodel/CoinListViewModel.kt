package com.ba.bitcointicker.viewmodel

import com.ba.bitcointicker.data.model.Coin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ba.bitcointicker.domain.usecase.GetCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase
) : ViewModel() {

    private val _coinList = MutableStateFlow<List<Coin>>(emptyList())
    val coinList: StateFlow<List<Coin>> = _coinList

    private val _filteredCoins = MutableStateFlow<List<Coin>>(emptyList())
    val filteredCoins: StateFlow<List<Coin>> = _filteredCoins

    init {
        fetchCoins()
    }

    private fun fetchCoins() {
        viewModelScope.launch {
            val coins = getCoinsUseCase.invoke()
            _coinList.value = coins
            _filteredCoins.value = coins
        }
    }

    fun searchCoin(query: String) {
        _filteredCoins.value = if (query.isEmpty()) {
            _coinList.value
        } else {
            _coinList.value.filter { it.name.contains(query, ignoreCase = true) }
        }
    }
}

