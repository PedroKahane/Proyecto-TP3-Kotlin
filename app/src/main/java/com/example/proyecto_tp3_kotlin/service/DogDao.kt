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

    @Query("update Dog_table SET adoptado = 1 where id = :id")
    fun Adoptar(id: Int)

    @Query("UPDATE Dog_table SET favorito = CASE WHEN favorito = 0 THEN 1 ELSE 0 END WHERE id = :id")
    fun favorito(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllDogs(dogs: List<DogModel>)

    @Query("SELECT * FROM Dog_table where favorito = 1" )
    fun getFavorites(): List<DogModel>

    @Query("SELECT * FROM Dog_table where adoptado = 1" )
    fun getAdoptado(): List<DogModel>

    @Query("Select * from Dog_table order by Id asc")
    fun readAllDate():LiveData<List<DogModel>>

    @Query("Select * from Dog_table WHERE Id = :id")
    fun getDetails(id: Long):LiveData<List<DogModel>>

    @Query("DELETE FROM dog_table")
    suspend fun borrarTodosLosPerros()

}