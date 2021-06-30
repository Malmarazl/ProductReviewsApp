package com.example.productreviewsapp.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productreviewsapp.models.Product
import com.example.productreviewsapp.models.Review
import com.example.productreviewsapp.services.ServiceAdapter
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {

    val product = MutableLiveData<Product>()
    val reviewList = MutableLiveData<List<Review>>()
    val review = MutableLiveData<Review>()

    fun getProduct(productID: String) {
        viewModelScope.launch {
            connectionToProductService(productID)
        }
    }

    fun getReviews(productID: String) {
        viewModelScope.launch {
            connectionToReviewService(productID)
        }
    }

    fun addNewReview(newReview: Review) {
        viewModelScope.launch {
            connectionToNewReviewService(newReview)
        }
    }

    private suspend fun connectionToProductService(productID: String) {

       val response = ServiceAdapter().getApiServiceProducts()?.getProductID(productID)
        product.postValue(response)
    }

    private suspend fun connectionToReviewService(productID: String) {

        val response = ServiceAdapter().getApiServiceReviews()?.getReviews(productID)
        reviewList.postValue(response)
    }

    private suspend fun connectionToNewReviewService(newReview: Review) {
        val response = ServiceAdapter().getApiServiceReviews()?.addReview(newReview.productId, newReview)
        review.postValue(response)
    }
}