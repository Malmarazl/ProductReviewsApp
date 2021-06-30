package com.example.productreviewsapp.models

import com.google.gson.annotations.SerializedName

data class Review (@SerializedName("productId") var productId: String = "",
                   @SerializedName("locale") var locale: String = "GB",
                   @SerializedName("rating") var rating: Float = 0f,
                   @SerializedName("text") var text: String = "")