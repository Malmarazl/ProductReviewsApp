package com.example.productreviewsapp.models

import com.google.gson.annotations.SerializedName

data class Product (@SerializedName("currency") var currency: String,
                    @SerializedName("price") var price: Int,
                    @SerializedName("name") var name: String,
                    @SerializedName("description") var description: String,
                    @SerializedName("id") var id: String,
                    @SerializedName("imgUrl") var imgUrl: String)
