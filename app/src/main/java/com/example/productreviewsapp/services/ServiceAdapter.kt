package com.example.productreviewsapp.services

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceAdapter {

    private var API_SERVICE_PRODUCTS: ApiService? = null
    private var API_SERVICE_REVIEWS: ApiService? = null

    fun getApiServiceProducts(): ApiService? {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        val baseUrl = "http://192.168.178.221:3001"

        if (API_SERVICE_PRODUCTS == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
            API_SERVICE_PRODUCTS = retrofit.create(ApiService::class.java)
        }

        return API_SERVICE_PRODUCTS
    }

    fun getApiServiceReviews(): ApiService? {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        val baseUrl = "http://192.168.178.221:3002"

        if (API_SERVICE_REVIEWS == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
            API_SERVICE_REVIEWS = retrofit.create(ApiService::class.java)
        }

        return API_SERVICE_REVIEWS
    }
}