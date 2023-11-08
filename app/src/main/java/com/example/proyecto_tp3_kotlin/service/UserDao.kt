package com.example.proyecto_tp3_kotlin.service

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.proyecto_tp3_kotlin.model.UserModel

@Dao
interface UserDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user : UserModel)

    @Query("select perrosAdoptados from User_table where id = :userId")
    fun getPerrosAdoptados(userId: Int): LiveData<List<Int>>

    @Query("select perrosFavoritos from User_table where id = :userId")
    fun getPerrosFavoritos(userId: Int): LiveData<List<Int>>


}