package com.example.productreviewsapp.services

import com.example.productreviewsapp.models.Product
import com.example.productreviewsapp.models.Review
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductService {
    @GET("product")
    suspend fun getProduct() : List<Product>

    @GET("product/{id}")
    suspend fun getProductID(
        @Path("id") id: String) : Product
}