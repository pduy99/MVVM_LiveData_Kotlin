package com.example.trendinggit.repositories

import android.util.Log
import com.example.trendinggit.GitAPI.ApiClient
import com.example.trendinggit.models.GitResponse
import com.example.trendinggit.models.Item
import retrofit2.Response
import retrofit2.Call
import retrofit2.Callback

//Singleton pattern
class RepoRepository {
    companion object {
        private var INSTANCE: RepoRepository? = null
        fun getInstance() = INSTANCE
            ?: RepoRepository().also {
                INSTANCE = it
            }
    }

    fun getRepoList(period : String, language : String, onResult: (isSuccess : Boolean, response: List<Item>?) -> Unit){
        ApiClient.instance.getRepo(period, language ).enqueue(object : Callback<List<Item>> {
            override fun onFailure(call: Call<List<Item>>?, t: Throwable?) {
                onResult(false,null)
            }

            override fun onResponse(call: Call<List<Item>>?, response: Response<List<Item>>?) {
                if(response != null && response.isSuccessful) {
                    onResult(true, response.body())
                    Log.d("getRepo_RESULT: ", "Successfull and body not empty")
                }
            }

        })
    }
}