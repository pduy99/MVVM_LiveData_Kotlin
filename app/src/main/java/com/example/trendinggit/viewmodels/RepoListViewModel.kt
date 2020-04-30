package com.example.trendinggit.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.trendinggit.BaseViewModel
import com.example.trendinggit.models.GitResponse
import com.example.trendinggit.models.Item
import com.example.trendinggit.repositories.RepoRepository

class RepoListViewModel : BaseViewModel() {
    val repoList = MutableLiveData<List<Item>>();
    lateinit var repoRepository : RepoRepository

    fun fetchRepoList(){
        repoRepository = RepoRepository.getInstance()
        isLoadingData.value = true;
        Log.d("FETCH REPO","BEGINNING")
        repoRepository.getRepoList { isSuccess, response ->
            isLoadingData.value = false
            if(isSuccess){
                repoList.value = response
                isEmpty.value = false
            }
            else{
                isEmpty.value = true
            }
        }
    }
}