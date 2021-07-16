package com.example.productreviewsapp.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productreviewsapp.models.Product
import com.example.productreviewsapp.models.Review
import com.example.productreviewsapp.services.ServiceAdapter
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailViewModel : ViewModel() {

    private val _product = MutableLiveData<Product>()
    private val _reviewList = MutableLiveData<List<Review>>()
    private val _review = MutableLiveData<Review>()
    private val _error = MutableLiveData<String>()

    val product: LiveData<Product> = _product
    val reviewList: LiveData<List<Review>> = _reviewList
    val review: LiveData<Review> = _review
    val error: LiveData<String> = _error

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
            val response = ServiceAdapter.getApiServiceProducts()?.getProductID(productID)
            _product.postValue(response)
        } catch (e: Exception){
            _error.postValue(e.message)
        }
    }

    private suspend fun connectionToReviewService(productID: String) {
        try {
            val response = ServiceAdapter.getApiServiceReviews()?.getReviews(productID)
            _reviewList.postValue(response)
        } catch (e: Exception) {
            _error.postValue(e.message)
        }
    }

    private suspend fun connectionToNewReviewService(newReview: Review) {
        try{
            val response = ServiceAdapter.getApiServiceReviews()?.addReview(newReview.productId, newReview)
            _review.postValue(response)
        } catch (e: Exception) {
            _error.postValue(e.message)
        }
    }
}