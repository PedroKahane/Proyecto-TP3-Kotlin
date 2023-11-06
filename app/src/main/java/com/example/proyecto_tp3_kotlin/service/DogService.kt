package com.example.proyecto_tp3_kotlin.service

import android.widget.TextView
import com.example.proyecto_tp3_kotlin.model.DoggModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
class DogService @Inject constructor(
) {
    suspend fun getDogsByFilter(filters: Map<String, String>): List<DoggModel> {
        val service = ActivityServiceBuilder.create()
        return withContext(Dispatchers.IO) {
            val response = service.getDogsByFilter(filters)
            response.body() ?: emptyList()
        }
    }
    suspend fun getBreeds(): List<String> {
        val service = ActivityServiceBuilder.create()
        return withContext(Dispatchers.IO) {
            val response = service.getAvailableBreeds()

            if (response.status == "success") {
                val breeds = response.message.keys.toList()

                breeds
            } else {

                emptyList()
            }
        }
    }
    suspend fun getSubBreeds(): Map<String, List<String>> {
        val service = ActivityServiceBuilder.create()
        return withContext(Dispatchers.IO) {
            val response = service.getAvailableBreeds()

            if (response.status == "success") {
                val subBreeds = mutableMapOf<String, List<String>>()

                for ((breed, subBreedsList) in response.message) {
                    if (subBreedsList.isNotEmpty()) {
                        subBreeds[breed] = subBreedsList
                    }
                }

                subBreeds
            } else {
                emptyMap()
            }
        }
    }
    suspend fun getBreedImage(breed: String): String? {
        val service = ActivityServiceBuilder.create()
        return withContext(Dispatchers.IO) {
            val response = service.getBreedDogImage(breed)

            if (response.status == "success") {
                response.message.toString()
            } else {
                null
            }
        }
    }
    suspend fun getSubBreedImage(breed: String, subBreed: String): String? {
        val service = ActivityServiceBuilder.create()
        return withContext(Dispatchers.IO) {
            val response = service.getSubBreedDogImage(breed, subBreed)

            if (response.status == "success") {
                response.message.toString()
            } else {
                null
            }
        }
    }
}