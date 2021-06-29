package com.example.productreviewsapp.services

import com.example.productreviewsapp.models.Product
import retrofit2.http.GET

interface ProductService {
    @GET("product")
    suspend fun getProduct() : List<Product>
}