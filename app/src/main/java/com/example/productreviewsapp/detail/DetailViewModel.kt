package com.example.productreviewsapp.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productreviewsapp.models.Product
import com.example.productreviewsapp.models.Review
import com.example.productreviewsapp.services.ServiceAdapter
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailViewModel : ViewModel() {

    val product = MutableLiveData<Product>()
    val reviewList = MutableLiveData<List<Review>>()
    val review = MutableLiveData<Review>()
    val error = MutableLiveData<String>()

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
        try {
            val response = ServiceAdapter().getApiServiceProducts()?.getProductID(productID)
            product.postValue(response)
        } catch (e: Exception){
            error.postValue(e.message)
        }
    }

    private suspend fun connectionToReviewService(productID: String) {
        try {
            val response = ServiceAdapter().getApiServiceReviews()?.getReviews(productID)
            reviewList.postValue(response)
        } catch (e: Exception) {
            error.postValue(e.message)
        }
    }

    private suspend fun connectionToNewReviewService(newReview: Review) {
        try{
            val response = ServiceAdapter().getApiServiceReviews()?.addReview(newReview.productId, newReview)
            review.postValue(response)
        } catch (e: Exception) {
            error.postValue(e.message)
        }
    }
}