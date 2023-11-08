package com.example.proyecto_tp3_kotlin.service

import androidx.lifecycle.LiveData
import com.example.proyecto_tp3_kotlin.model.DogModel
import com.example.proyecto_tp3_kotlin.model.UserModel
import javax.inject.Inject

class UserRepository @Inject constructor(private val userDao: UserDao, val user: UserModel) {



    suspend fun addUser(user: UserModel){
        userDao.addUser(user)
    }

    val getPerrosAdoptados : LiveData<List<Int>> = userDao.getPerrosAdoptados(user.id)


    val getPerrosFavoritos : LiveData<List<Int>> = userDao.getPerrosFavoritos(user.id)

}