package com.ba.bitcointicker.viewmodel

import androidx.lifecycle.ViewModel
import com.ba.bitcointicker.data.model.Coin
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _favoriteCoins = MutableStateFlow<List<Coin>>(emptyList())
    val favoriteCoins: StateFlow<List<Coin>> = _favoriteCoins

    fun fetchFavoriteCoins() {
        val userId = auth.currentUser?.uid ?: return

        firestore.collection("users")
            .document(userId)
            .collection("favorites")
            .get()
            .addOnSuccessListener { documents ->
                val coins = documents.mapNotNull { it.toObject(Coin::class.java) }
                _favoriteCoins.value = coins
            }
            .addOnFailureListener { e ->
                println("Error occurred when pulling favorites: ${e.message}")
            }
    }
}
