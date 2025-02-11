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
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _favorites = MutableStateFlow<List<Coin>>(emptyList())
    val favorites: StateFlow<List<Coin>> = _favorites

    fun fetchFavoriteCoins() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        firestore.collection("users")
            .document(userId)
            .collection("favorites")
            .get()
            .addOnSuccessListener { documents ->
                val coins = documents.map { it.toObject(Coin::class.java) }
                _favorites.value = coins
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreError", "Favorileri çekerken hata oluştu: ${e.message}")
            }
    }
}
