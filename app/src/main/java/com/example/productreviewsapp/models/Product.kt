package com.example.productreviewsapp.models

import com.google.gson.annotations.SerializedName

data class Product (@SerializedName("currency") val currency: String,
                    @SerializedName("price") val price: Int,
                    @SerializedName("name") val name: String,
                    @SerializedName("description") val description: String,
                    @SerializedName("id") val id: String,
                    @SerializedName("imgUrl") val imgUrl: String)
