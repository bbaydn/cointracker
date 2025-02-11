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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
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
            val bundle = Bundle().apply { putString("coin_id", coinId) }
            findNavController().navigate(R.id.action_favoritesFragment_to_coinDetailFragment, bundle)
        }

        binding.rvFavorites.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.favoriteCoins.collectLatest { coins ->
                adapter.submitList(coins)
            }
        }

        viewModel.fetchFavoriteCoins()
    }
}
