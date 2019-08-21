package ch.wyler.microspot.models

import com.google.gson.annotations.SerializedName

data class Pagination(
    @SerializedName("totalNumberOfResults")
    val totalNumberOfResults: Int
)