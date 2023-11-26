package com.example.proyecto_tp3_kotlin.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel(){
    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _isDarkMode = MutableLiveData<Boolean>()
    val isDarkMode: LiveData<Boolean> = _isDarkMode


    fun setDarkMode(isDarkMode: Boolean) {
        _isDarkMode.value = isDarkMode
    }


}