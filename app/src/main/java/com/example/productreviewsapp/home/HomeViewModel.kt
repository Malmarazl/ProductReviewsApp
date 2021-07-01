package com.example.productreviewsapp.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productreviewsapp.models.Product
import com.example.productreviewsapp.services.ServiceAdapter
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel : ViewModel() {

    val productList = MutableLiveData<List<Product>>()
    val error = MutableLiveData<String>()

    fun getProducts() {
        viewModelScope.launch {
            connectionToProductService()
        }
    }

    private suspend fun connectionToProductService() {
        try {
            val response = ServiceAdapter().getApiServiceProducts()?.getProduct()
            productList.postValue(response)
        } catch (e: Exception) {
            error.postValue(e.message)
        }

    }

}