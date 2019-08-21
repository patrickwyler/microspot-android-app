package ch.wyler.microspot.models

import com.google.gson.annotations.SerializedName

data class CustomImageData(
    @SerializedName("sizes")
    val images: List<Image>
)