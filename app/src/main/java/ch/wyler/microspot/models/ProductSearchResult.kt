package ch.wyler.microspot.models

import com.google.gson.annotations.SerializedName

data class ProductSearchResult(
    @SerializedName("pagination")
    val pagination: Pagination,
    @SerializedName("products")
    val products: List<Product>
)