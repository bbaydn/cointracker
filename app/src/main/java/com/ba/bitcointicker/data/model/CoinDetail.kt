package com.ba.bitcointicker.data.model

import com.google.gson.annotations.SerializedName

data class CoinDetail(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("symbol") val symbol: String,
    @SerializedName("image") val image: ImageData,
    @SerializedName("market_data") val marketData: MarketData,
    @SerializedName("hashing_algorithm") val hashingAlgorithm: String?,
    @SerializedName("description") val description: Description
) {
    val price: Double
        get() = marketData.currentPrice["usd"] ?: 0.0

    val priceChange: Double
        get() = marketData.priceChangePercentage24h ?: 0.0

    val imageUrl: String
        get() = image.large
}

data class Description(
    @SerializedName("en") val en: String
)


data class ImageData(
    @SerializedName("large") val large: String
)

data class MarketData(
    @SerializedName("current_price") val currentPrice: Map<String, Double>,
    @SerializedName("price_change_percentage_24h") val priceChangePercentage24h: Double?

)

fun CoinDetail.toCoin(): Coin {
    return Coin(
        id = this.id,
        name = this.name,
        symbol = this.symbol,
        price = this.price,
        image = this.imageUrl
    )
}



