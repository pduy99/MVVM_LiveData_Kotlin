package com.example.trendinggit.GitAPI

import com.example.trendinggit.models.Item
import retrofit2.http.Query
import retrofit2.Call
import retrofit2.http.GET

interface ApiService{

    @GET("repositories")
    fun getRepo(@Query("since") since:String, @Query("language") language: String) : Call<List<Item>>

}