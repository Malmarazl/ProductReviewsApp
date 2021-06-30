package com.example.productreviewsapp.detail

interface NewReviewListener {
    fun sendNewReview(newReview: String, starReview: Float)
}