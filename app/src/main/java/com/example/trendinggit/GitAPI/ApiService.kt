package com.example.trendinggit.GitAPI

import com.example.trendinggit.models.GitResponse
import com.example.trendinggit.models.Item
import retrofit2.http.Query
import retrofit2.Call
import retrofit2.http.GET

interface ApiService{

    @GET("repositories")
    fun getRepo(@Query("since") since:String = "weekly"):Call<List<Item>>
}