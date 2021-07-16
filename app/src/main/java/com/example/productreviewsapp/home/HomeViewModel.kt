package com.example.productreviewsapp.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productreviewsapp.models.Product
import com.example.productreviewsapp.services.ServiceAdapter
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel : ViewModel() {

    private val _productList = MutableLiveData<List<Product>>()
    private val _filteredList = MutableLiveData<List<Product>>()
    private val _error = MutableLiveData<String>()

    val productList:  LiveData<List<Product>> = _productList
    val error: LiveData<String> = _error
    val filtereredList = _filteredList

    fun getProducts() {
        viewModelScope.launch {
            connectionToProductService()
        }
    }

    private suspend fun connectionToProductService() {
        try {
            val response = ServiceAdapter.getApiServiceProducts()?.getProduct()
            _productList.postValue(response)
        } catch (e: Exception) {
            _error.postValue(e.message)
        }

    }

    fun filterList(filterText :String) {
        if (filterText.isEmpty())
            _filteredList.postValue(productList.value)
        else {
            _productList.postValue(
                _filteredList.value?.filter {
                    it.name.lowercase().contains(filterText.lowercase()) || it.description.lowercase().contains(filterText.lowercase())
                }
            )
        }
    }
}