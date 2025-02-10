package com.ba.bitcointicker.ui.coinlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ba.bitcointicker.data.remote.Retrofit
import com.ba.bitcointicker.data.repository.CoinRepository
import com.ba.bitcointicker.databinding.FragmentCoinListBinding
import com.ba.bitcointicker.domain.usecase.GetCoinsUseCase
import com.ba.bitcointicker.viewmodel.CoinListViewModel
import com.ba.bitcointicker.viewmodel.CoinListViewModelFactory
import kotlinx.coroutines.launch

class CoinListFragment : Fragment() {

    private lateinit var binding: FragmentCoinListBinding
    private lateinit var viewModel: CoinListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        // I will do something here
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCoinListBinding.inflate(layoutInflater, container, false)
        val getCoinsUseCase = GetCoinsUseCase(CoinRepository(Retrofit.api))
        val factory = CoinListViewModelFactory(getCoinsUseCase)
        viewModel = ViewModelProvider(this, factory)[CoinListViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = CoinListAdapter()
        binding.rvCoinList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCoinList.adapter = adapter

        lifecycleScope.launch {
            viewModel.coinList.collect { coins ->
                adapter.submitList(coins)
            }
        }

        viewModel.fetchCoins()
    }
}