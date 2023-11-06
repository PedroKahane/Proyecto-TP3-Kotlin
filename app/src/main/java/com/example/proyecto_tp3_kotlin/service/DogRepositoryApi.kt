package com.example.proyecto_tp3_kotlin.service

import com.example.proyecto_tp3_kotlin.model.BreedModel
import com.example.proyecto_tp3_kotlin.model.DogModel

import javax.inject.Inject

class DogRepositoryApi @Inject constructor(
    private val remote: DogService
) {
    suspend fun getDogsByFilter(filters: Map<String, String>): List<DogModel> {
        return remote.getDogsByFilter(filters)
    }

    suspend fun getAvailableBreeds(): List<String> {
        return remote.getBreeds()
    }
    suspend fun getAvailableSubBreeds(): Map<String, List<String>> {
        return remote.getSubBreeds()
    }
    suspend fun getBreedImage(breed: String): String? {
        return remote.getBreedImage(breed)
    }
    suspend fun getSubBreedImage(breed: String, subBreed: String): String? {
        return remote.getSubBreedImage(breed, subBreed)

    }
}