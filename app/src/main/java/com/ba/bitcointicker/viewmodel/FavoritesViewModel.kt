package com.ba.bitcointicker.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ba.bitcointicker.data.model.Coin
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _favoriteCoins = MutableStateFlow<List<Coin>>(emptyList())
    val favoriteCoins: StateFlow<List<Coin>> = _favoriteCoins

    fun fetchFavoriteCoins() {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            println("FirebaseAuth: Kullanıcı giriş yapmamış!")
            return
        }

        firestore.collection("users")
            .document(userId)
            .collection("favorites")
            .get()
            .addOnSuccessListener { documents ->
                val coins = documents.mapNotNull { it.toObject(Coin::class.java) }
                _favoriteCoins.value = coins
                println("Favori coinler başarıyla çekildi: ${coins.size} adet")
            }
            .addOnFailureListener { e ->
                println("Favori coinler alınırken hata oluştu: ${e.message}")
            }
    }
}
