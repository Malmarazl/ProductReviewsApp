package com.example.productreviewsapp.services

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceAdapter {

    private var API_SERVICE: ProductService? = null

    fun getApiService(): ProductService? {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        val baseUrl = "http://192.168.178.221:3001"

        if (API_SERVICE == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
            API_SERVICE = retrofit.create(ProductService::class.java)
        }

        return API_SERVICE
    }

}