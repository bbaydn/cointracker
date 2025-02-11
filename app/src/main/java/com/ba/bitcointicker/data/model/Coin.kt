package com.ba.bitcointicker.data.model

import com.google.gson.annotations.SerializedName

data class Coin(
    @SerializedName("id") val id: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("symbol") val symbol: String = "",
    @SerializedName("current_price") val price: Double = 0.0,
    @SerializedName("image") val image: String = ""
) {
    constructor() : this("", "", "", 0.0)
}


