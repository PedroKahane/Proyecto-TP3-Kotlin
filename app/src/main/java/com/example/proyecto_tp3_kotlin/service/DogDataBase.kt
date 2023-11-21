package com.example.proyecto_tp3_kotlin.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.proyecto_tp3_kotlin.model.DogModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(entities = [DogModel:: class], version = 5, exportSchema = false)
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
                )
                    .addCallback(roomCallback)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                initializeDatabase(instance)
                return instance
            }
        }
        private fun initializeDatabase(database: DogDataBase) {
            // Utilizar una coroutine para llenar la base de datos en un hilo secundario
            CoroutineScope(Dispatchers.IO).launch {
                // Lógica para llenar la base de datos con perros iniciales
                val dogDao = database.dogDao()
                // Insertar perros iniciales
                //dogDao.borrarTodosLosPerros()
                if (dogDao.getAll().isEmpty()) {
                    val initialDogs = listOf(
                        DogModel(
                            name = "Chicho",
                            age = 4,
                            gender = "Macho",
                            weight = 8,
                            owner = "Oscar",
                            ubication = "Buenos Aires",
                            esFavorito = false,
                            adoptado = true,
                            breed = "akita",
                            subBreed = "",
                            image = "https://images.dog.ceo/breeds/akita/Akita_inu_blanc.jpg"
                        ),
                        DogModel(
                            name = "Peca",
                            age = 1,
                            gender = "Hembra",
                            weight = 18,
                            owner = "Juan",
                            ubication = "Buenos Aires",
                            esFavorito = false,
                            adoptado = true,
                            breed = "terrier",
                            subBreed = "dandie",
                            image = "https://images.dog.ceo/breeds/terrier-dandie/n02096437_4435.jpg"
                        ),
                        DogModel(
                            name = "Lenny",
                            age = 3,
                            gender = "Hembra",
                            weight = 20,
                            owner = "Ramiro",
                            ubication = "Buenos Aires",
                            esFavorito = false,
                            adoptado = false,
                            breed = "terrier",
                            subBreed = "fox",
                            image = "https://images.dog.ceo/breeds/terrier-fox/n02095314_3512.jpg"
                        ),
                        DogModel(
                            name = "Rafael",
                            age = 19,
                            gender = "Macho",
                            weight = 12,
                            owner = "Santiago",
                            ubication = "Buenos Aires",
                            esFavorito = false,
                            adoptado = false,
                            breed = "pomeranian",
                            subBreed = "",
                            image = "https://images.dog.ceo/breeds/pomeranian/n02112018_1389.jpg"
                        ),
                        DogModel(
                            name = "Perro",
                            age = 10,
                            gender = "Macho",
                            weight = 30,
                            owner = "Sofia",
                            ubication = "Buenos Aires",
                            esFavorito = false,
                            adoptado = true,
                            breed = "pembroke",
                            subBreed = "",
                            image = "https://images.dog.ceo/breeds/pembroke/n02113023_3927.jpg"
                        ),
                        DogModel(
                            name = "Toto",
                            age = 12,
                            gender = "Macho",
                            weight = 15,
                            owner = "Juan",
                            ubication = "Buenos Aires",
                            esFavorito = false,
                            adoptado = false,
                            breed = "pekinese",
                            subBreed = "",
                            image = "https://images.dog.ceo/breeds/pekinese/n02086079_7302.jpg"
                        ),
                        DogModel(
                            name = "Bart",
                            age = 4,
                            gender = "Macho",
                            weight = 25,
                            owner = "Delfina",
                            ubication = "Buenos Aires",
                            esFavorito = true,
                            adoptado = false,
                            breed = "havanese",
                            subBreed = "",
                            image = "https://images.dog.ceo/breeds/havanese/00100trPORTRAIT_00100_BURST20191103202017556_COVER.jpg"
                        ),
                        DogModel(
                            name = "Lola",
                            age = 5,
                            gender = "Hembra",
                            weight = 22,
                            owner = "Juana",
                            ubication = "Buenos Aires",
                            esFavorito = false,
                            adoptado = false,
                            breed = "hound",
                            subBreed = "blood",
                            image = "https://images.dog.ceo/breeds/hound-blood/n02088466_5898.jpg"
                        ),
                        DogModel(
                            name = "Kira",
                            age = 2,
                            gender = "Hembra",
                            weight = 9,
                            owner = "Luana",
                            ubication = "Buenos Aires",
                            esFavorito = true,
                            adoptado = false,
                            breed = "poodle",
                            subBreed = "toy",
                            image = "https://images.dog.ceo/breeds/poodle-toy/n02113624_223.jpg"
                        ),
                        DogModel(
                            name = "Magui",
                            age = 2,
                            gender = "Hembra",
                            weight = 24,
                            owner = "Franco",
                            ubication = "Buenos Aires",
                            esFavorito = false,
                            adoptado = false,
                            breed = "doberman",
                            subBreed = "",
                            image = "https://images.dog.ceo/breeds/doberman/n02107142_699.jpg"
                        ),
                        DogModel(
                            name = "Teo",
                            age = 4,
                            gender = "Macho",
                            weight = 11,
                            owner = "Luciana",
                            ubication = "Buenos Aires",
                            esFavorito = false,
                            adoptado = false,
                            breed = "mountain",
                            subBreed = "bernese",
                            image = "https://images.dog.ceo/breeds/mountain-bernese/n02107683_3900.jpg"
                        ),
                    )
                    dogDao.insertAllDogs(initialDogs)
                }
            }
        }
        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
            }
        }
        fun destroyDatabase(){
            INSTANCE = null
        }

    }
}