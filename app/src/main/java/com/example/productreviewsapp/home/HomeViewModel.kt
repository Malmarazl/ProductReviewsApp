package com.example.productreviewsapp.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productreviewsapp.models.Product
import com.example.productreviewsapp.services.ServiceAdapter
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    val productList = MutableLiveData<List<Product>>()

    fun getProducts() {
        viewModelScope.launch {
            connectionToProductService()
        }
    }

    private suspend fun connectionToProductService() {
        val response = ServiceAdapter().getApiServiceProducts()?.getProduct()
        productList.postValue(response)
    }

}