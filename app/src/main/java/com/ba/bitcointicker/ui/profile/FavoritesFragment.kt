package com.ba.bitcointicker.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ba.bitcointicker.R
import com.ba.bitcointicker.databinding.FragmentFavoritesBinding
import com.ba.bitcointicker.ui.coinlist.CoinListAdapter
import com.ba.bitcointicker.viewmodel.FavoritesViewModel
import kotlinx.coroutines.launch

class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private val viewModel: FavoritesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CoinListAdapter { coinId ->
            findNavController().navigate(R.id.action_favoritesFragment_to_coinDetailFragment)
        }

        binding.apply {
            rvFavorites.layoutManager = LinearLayoutManager(requireContext())
            rvFavorites.adapter = adapter
        }

        lifecycleScope.launch {
            viewModel.favorites.collect { coins ->
                adapter.submitList(coins)
            }
        }

        viewModel.fetchFavoriteCoins()
    }
}
