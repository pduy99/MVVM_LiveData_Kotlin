package com.example.trendinggit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    val isEmpty = MutableLiveData<Boolean>().apply { value = false };
    val isLoadingData = MutableLiveData<Boolean>().apply { value = false };
    val toastMessage = MutableLiveData<String>()
}