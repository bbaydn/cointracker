package com.ba.bitcointicker.ui.coinlist

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ba.bitcointicker.R
import com.ba.bitcointicker.data.model.Coin
import com.ba.bitcointicker.databinding.FragmentCoinListBinding
import com.ba.bitcointicker.viewmodel.CoinListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CoinListFragment : Fragment() {

    private lateinit var binding: FragmentCoinListBinding
    private val viewModel: CoinListViewModel by viewModels()
    private lateinit var adapter: CoinListAdapter
    private var allCoins = listOf<Coin>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCoinListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CoinListAdapter { coinId ->
            findNavController().navigate(
                R.id.action_coinListFragment_to_coinDetailFragment,
                bundleOf("coin_id" to coinId)
            )
        }

        binding.apply {
            rvCoinList.layoutManager = LinearLayoutManager(requireContext())
            rvCoinList.adapter = adapter
            etSearch.addTextChangedListener {
                viewModel.searchCoin(it.toString())
            }
        }

        lifecycleScope.launch {
            viewModel.filteredCoins.collect { coins ->
                allCoins = coins
                adapter.submitList(allCoins)
            }
        }

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                searchCoin(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun searchCoin(query: String) {
        val filteredList = allCoins.filter { coin ->
            coin.name.contains(query, ignoreCase = true) ||
                    coin.symbol.contains(query, ignoreCase = true)
        }
        adapter.submitList(filteredList)
    }
}
