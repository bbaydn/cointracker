package com.ba.bitcointicker.ui.coinlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ba.bitcointicker.R
import com.ba.bitcointicker.data.model.Coin
import com.ba.bitcointicker.databinding.ItemsViewBinding
import com.bumptech.glide.Glide

class CoinListAdapter(private val onItemClick: (String) -> Unit) :
    RecyclerView.Adapter<CoinListAdapter.CoinViewHolder>() {

    private val coinList = mutableListOf<Coin>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<Coin>) {
        coinList.clear()
        coinList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val binding = ItemsViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        holder.bind(coinList[position])
    }

    override fun getItemCount(): Int = coinList.size

    inner class CoinViewHolder(
        private val binding: ItemsViewBinding,
        private val onItemClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(coin: Coin) {
            binding.apply {
                tvCoinName.text = coin.name
                tvCoinPrice.text = "${coin.price}"
                root.setOnClickListener {
                    onItemClick(coin.id)
                }
                Glide.with(ivCoinIcon.context)
                    .load(coin.image)
                    .placeholder(R.drawable.money_icon)
                    .error(R.drawable.money_icon)
                    .into(ivCoinIcon)
            }
        }
    }
}
