package com.example.proyecto_tp3_kotlin.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Dog_table")
class DogModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "image")
    val image: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "age")
    val age: Int,

    @ColumnInfo(name = "gender")
    val gender: String,

    @ColumnInfo(name = "weight")
    val weight: Int,

    @ColumnInfo(name = "breed")
    val breed: String,

    @ColumnInfo(name = "subBreed")
    val subBreed: String,

    @ColumnInfo(name = "owner")
    val owner: String,

    @ColumnInfo(name = "ubication")
    val ubication: String
){
    override fun toString(): String {
        return "DogModel(name=$name, breed=$breed, subBreed=$subBreed, owner=$owner, ...)"
    }
}


