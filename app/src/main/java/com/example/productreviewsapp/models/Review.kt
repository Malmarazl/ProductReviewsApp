package com.example.productreviewsapp.models

import com.google.gson.annotations.SerializedName

data class Review (@SerializedName("productId") val productId: String,
                   @SerializedName("locale") val locale: String,
                   @SerializedName("rating") val rating: Float,
                   @SerializedName("text") val text: String)