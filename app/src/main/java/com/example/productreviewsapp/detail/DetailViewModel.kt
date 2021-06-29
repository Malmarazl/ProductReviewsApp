package com.example.productreviewsapp.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productreviewsapp.models.Product
import com.example.productreviewsapp.services.ServiceAdapter
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {

    val product = MutableLiveData<Product>()

    fun getProduct(productID: String) {
        viewModelScope.launch {
            connectionToProductService(productID)
        }
    }

    private suspend fun connectionToProductService(productID: String) {

       val response = ServiceAdapter().getApiService()?.getProductID(productID)
        product.postValue(response)
    }
}