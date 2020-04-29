package com.example.trendinggit.GitAPI

import com.example.trendinggit.contants.Contants.Companion.DEBUG
import com.example.trendinggit.contants.Contants.Companion.REQUEST_TIME_OUT
import com.example.trendinggit.contants.Contants.Companion.URL
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object ApiClient{
    val instance : ApiService = Retrofit.Builder().run{
        val gson = GsonBuilder()
            .enableComplexMapKeySerialization()
            .setPrettyPrinting()
            .create();

        baseUrl(URL)
        addConverterFactory(GsonConverterFactory.create())
        client(createRequestInterceptorClient())
        build()
    }.create(ApiService::class.java)
}

private fun createRequestInterceptorClient() : OkHttpClient{
    val interceptor = Interceptor{chain ->
        val origin = chain.request()
        val requestBuilder = origin.newBuilder()
        val request = requestBuilder.build()
        chain.proceed(request)
    }

    return if (DEBUG){
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(REQUEST_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIME_OUT.toLong(),TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIME_OUT.toLong(),TimeUnit.SECONDS)
            .build()
    }else{
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(REQUEST_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIME_OUT.toLong(),TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIME_OUT.toLong(),TimeUnit.SECONDS)
            .build()
    }
}