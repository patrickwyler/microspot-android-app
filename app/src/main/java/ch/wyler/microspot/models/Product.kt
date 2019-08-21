package ch.wyler.microspot.models

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("code")
    val code: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("customImageData")
    val images: List<CustomImageData>
)