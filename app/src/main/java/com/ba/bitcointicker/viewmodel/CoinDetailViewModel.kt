package com.ba.bitcointicker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ba.bitcointicker.data.model.Coin
import com.ba.bitcointicker.data.model.CoinDetail
import com.ba.bitcointicker.data.repository.CoinRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val repository: CoinRepository,
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _coinDetail = MutableStateFlow<CoinDetail?>(null)
    val coinDetail: StateFlow<CoinDetail?> = _coinDetail

    fun fetchCoinDetail(coinId: String) {
        viewModelScope.launch {
            try {
                val detail = repository.getCoinDetail(coinId)
                _coinDetail.value = detail
            } catch (e: Exception) {
                println("API Error: ${e.message}")
            }
        }
    }

    fun addCoinToFavorites(coin: Coin, onSuccess: () -> Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        val userFavoritesRef = firestore.collection("users").document(userId).collection("favorites")

        userFavoritesRef.document(coin.id).set(coin)
            .addOnSuccessListener {
                println("Coin added to favorites list successfully!")
                onSuccess()
            }
            .addOnFailureListener { e ->
                println("Error: Couldn't add to favorite list. ${e.message}")
            }
    }

}
