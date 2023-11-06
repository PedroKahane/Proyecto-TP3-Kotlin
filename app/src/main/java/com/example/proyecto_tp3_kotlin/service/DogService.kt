package com.example.proyecto_tp3_kotlin.service

import com.example.proyecto_tp3_kotlin.model.BreedModel
import com.example.proyecto_tp3_kotlin.model.DogModel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
class DogService @Inject constructor(
) {
    suspend fun getDogsByFilter(filters: Map<String, String>): List<DogModel> {
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

    suspend fun getImage(breed: String, subBreed: String): String {
        val service = ActivityServiceBuilder.create()
        return withContext(Dispatchers.IO) {
            var response = service.getBreedDogImage(breed)
            if(subBreed != null){
                response = service.getSubBreedDogImage(breed, subBreed)
            }


            if (response.status == "success") {
                response.message.toString()
            } else {
                "https://images.dog.ceo/breeds/schnauzer-miniature/n02097047_6434.jpg"
            }
        }
    }
}