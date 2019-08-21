package ch.wyler.microspot.models

import com.google.gson.annotations.SerializedName

data class SearchApiRequest(
    @SerializedName("products")
    val products: List<Product>
)