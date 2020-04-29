package com.example.trendinggit.repositories

import android.util.Log
import com.example.trendinggit.GitAPI.ApiClient
import com.example.trendinggit.models.GitResponse
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

    fun getRepoList(onResult: (isSuccess : Boolean, response: GitResponse?) -> Unit){
        ApiClient.instance.getRepo().enqueue(object : Callback<GitResponse>{

            override fun onResponse(call : Call<GitResponse>,response: Response<GitResponse>?){
                if(response != null && response.isSuccessful) {
                    onResult(true, response.body() as @kotlin.ParameterName(name = "response") GitResponse)
                    Log.d("getRepo_RESULT: ", "Successfull and body not empty")
                }
                else{
                    onResult(false,null);
                    Log.d("getRepo_RESULT: ", "Successfull but body is empty")
                }
            }

            override fun onFailure(call: Call<GitResponse>?, t: Throwable?) {
                onResult(false,null);
                Log.d("getRepo_RESULT: ", "Failed")
            }
        })
    }
}