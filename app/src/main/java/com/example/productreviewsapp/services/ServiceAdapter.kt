package com.example.productreviewsapp.services

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceAdapter {

    private var API_SERVICE_PRODUCTS: ProductService? = null
    private var API_SERVICE_REVIEWS: ReviewService? = null

    fun getApiServiceProducts(): ProductService? {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        val baseUrl = "http://localhost:3001"

        if (API_SERVICE_PRODUCTS == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
            API_SERVICE_PRODUCTS = retrofit.create(ProductService::class.java)
        }

        return API_SERVICE_PRODUCTS
    }

    fun getApiServiceReviews(): ReviewService? {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        val baseUrl = "http://localhost:3002"

        if (API_SERVICE_REVIEWS == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
            API_SERVICE_REVIEWS = retrofit.create(ReviewService::class.java)
        }

        return API_SERVICE_REVIEWS
    }
}