package com.example.productreviewsapp.services

import com.example.productreviewsapp.models.Review
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ReviewService {
    @GET("reviews/{id}")
    suspend fun getReviews(
        @Path("id") id: String) : List<Review>

    @POST("reviews/{id}")
    suspend fun addReview(
        @Path("id") id: String,
        @Body newReview: Review
    ) : Review
}