package com.example.proyecto_tp3_kotlin.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.proyecto_tp3_kotlin.model.DogModel


@Database(entities = [DogModel:: class], version = 3, exportSchema = false)
abstract class DogDataBase: RoomDatabase() {

    abstract fun dogDao(): DogDao

    companion object {

        @Volatile
        private var INSTANCE : DogDataBase? = null

        fun getDatabase(context: Context): DogDataBase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DogDataBase::class.java,
                    "Dog_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

        fun destroyDatabase(){
            INSTANCE = null
        }

    }
}