package com.example.productreviewsapp.detail

interface NewReviewListener {
    fun sendNewReview(text: String, starReview: Float)
}