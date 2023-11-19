package com.example.proyecto_tp3_kotlin.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.proyecto_tp3_kotlin.model.DogModel
import com.example.proyecto_tp3_kotlin.service.DogDataBase
import com.example.proyecto_tp3_kotlin.service.DogRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DogViewModel(application: Application): AndroidViewModel(application) {

    private val readAllData: LiveData<List<DogModel>>
    private val repository: DogRepository

    init{

        val dogDao = DogDataBase.getDatabase(application).dogDao()
        repository = DogRepository(dogDao)
        readAllData = repository.readAllData
    }

    fun addDog(dog : DogModel){
        viewModelScope.launch(Dispatchers.IO){
            repository.addDog(dog)
        }

    }

}