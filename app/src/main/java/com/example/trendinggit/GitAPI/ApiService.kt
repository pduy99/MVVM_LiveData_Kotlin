package com.example.trendinggit.GitAPI

import com.example.trendinggit.models.GitResponse
import retrofit2.http.Query
import retrofit2.Call
import retrofit2.http.GET

interface ApiService{

    @GET("search/repositories")
    fun getRepo(@Query("q") search:String = "trending", @Query("sort") sort:String = "stars"):Call<GitResponse>
}