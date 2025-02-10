package com.ba.bitcointicker.ui.coindetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ba.bitcointicker.databinding.FragmentCoinDetailBinding
import com.ba.bitcointicker.viewmodel.CoinDetailViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CoinDetailFragment : Fragment() {
    private lateinit var binding: FragmentCoinDetailBinding

    private val viewModel: CoinDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCoinDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val coinId = arguments?.getString("coin_id") ?: return

        viewModel.fetchCoinDetail(coinId)

        binding.btnAddFavorite.setOnClickListener {
            viewModel.addCoinToFavorites(coinId)
        }

        lifecycleScope.launch {
            viewModel.coinDetail.collect { coin ->
                coin?.let {
                    binding.apply {
                        tvCoinName.text = it.name
                        tvCoinSymbol.text = "(${it.symbol.uppercase()})"
                        tvCoinPrice.text = "$${it.price}"
                        tvHashingAlgorithm.text = it.hashingAlgorithm ?: " "
                        tvDescription.text = it.description.en
                        tvPriceChangePercentage.text ="24h Change: " + it.priceChange.toString() + "%"
                    }

                    Glide.with(this@CoinDetailFragment)
                        .load(it.imageUrl)
                        .into(binding.ivCoinImage)
                }
            }
        }
    }
}
