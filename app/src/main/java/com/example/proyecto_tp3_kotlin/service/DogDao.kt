package com.example.proyecto_tp3_kotlin.service

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.proyecto_tp3_kotlin.model.DogModel


@Dao
interface DogDao {

    @Query("SELECT * FROM Dog_table")
    fun getAll(): List<DogModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun instertAll(vararg dog: DogModel)

    @Query("Select * from Dog_table order by Id asc")
    fun readAllDate():LiveData<List<DogModel>>

    @Query("Select * from Dog_table WHERE Id = :id")
    fun getDetails(id: Long):LiveData<List<DogModel>>
}