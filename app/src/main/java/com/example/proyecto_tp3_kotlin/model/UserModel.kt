package com.example.proyecto_tp3_kotlin.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User_table")
class UserModel (id: Int, telefono: String, perrosAdoptados : List<Int>, perrosFavoritos: List<Int> ){

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id : Int

    @ColumnInfo(name = "telefono")
    val telefono : String

    @ColumnInfo(name = "perrosAdoptados")
    val perrosAdoptados: List<Int>

    @ColumnInfo(name = "perrosFavoritos")
    val perrosFavoritos : List<Int>


    init{
        this.id = id
        this.telefono = telefono
        this.perrosAdoptados = perrosAdoptados
        this.perrosFavoritos = perrosFavoritos
    }




}