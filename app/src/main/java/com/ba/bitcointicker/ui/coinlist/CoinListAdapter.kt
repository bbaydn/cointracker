package com.ba.bitcointicker.ui.coinlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ba.bitcointicker.data.model.Coin
import com.ba.bitcointicker.databinding.ItemsViewBinding

class CoinListAdapter: RecyclerView.Adapter<CoinListAdapter.CoinViewHolder>() {
    private val coinList = mutableListOf<Coin>()

    fun submitList(newList: List<Coin>) {
        coinList.clear()
        coinList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val binding = ItemsViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        holder.bind(coinList[position])
    }

    override fun getItemCount(): Int = coinList.size

    class CoinViewHolder(private val binding: ItemsViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(coin: Coin) {
            binding.apply {
                textViewCoinName.text = coin.name
                textViewCoinPrice.text = "${coin.price}"
            }
        }
    }
}