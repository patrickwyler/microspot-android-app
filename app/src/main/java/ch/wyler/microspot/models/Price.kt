package ch.wyler.microspot.models

import com.google.gson.annotations.SerializedName

data class Price(
    @SerializedName("formattedValue")
    val formattedValue: String
)