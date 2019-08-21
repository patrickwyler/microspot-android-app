package ch.wyler.microspot.models

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("size")
    val size: Int,
    @SerializedName("url")
    val url: String
)