package com.example.proyecto_tp3_kotlin.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Dog_table")
class DogModel (id : Int, image : String,name : String, age: Int, gender: String, weight: Int, breed : String, subBreed : String, owner: String, ubication : String) {

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id : Int

    @ColumnInfo(name = "image")
    val image : String

    @ColumnInfo(name = "name")
    val name: String

    @ColumnInfo(name = "age")
    val age : Int

    @ColumnInfo(name = "gender")
    val gender : String

    @ColumnInfo(name = "weight")
    val weight : Int

    @ColumnInfo(name = "breed")
    val breed : String

    @ColumnInfo(name = "subBreed")
    val subBreed: String

    @ColumnInfo(name = "owner")
    val owner : String

    @ColumnInfo(name = "ubication")
    val ubication : String

    init{
        this.id = id
        this.image = image
        this.name = name
        this.age = age
        this.gender = gender
        this.weight = weight
        this.breed = breed
        this.subBreed = subBreed
        this.owner = owner
        this.ubication = ubication

    }

}


