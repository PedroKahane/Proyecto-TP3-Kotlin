package com.example.proyecto_tp3_kotlin.service

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.proyecto_tp3_kotlin.model.DogModel

import javax.inject.Inject

class DogRepository @Inject constructor(private val dogDao: DogDao) {


    val readAllData: LiveData<List<DogModel>> = dogDao.readAllDate()

    suspend fun addDog(dog: DogModel){
        dogDao.addDog(dog)
        Log.d("MyTag", "Elemento insertado con ID: $dog")
    }
    suspend fun getDog(id: Long){
        dogDao.getDetails(id)
    }

}