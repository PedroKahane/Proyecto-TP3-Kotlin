package com.example.proyecto_tp3_kotlin.service

import com.example.proyecto_tp3_kotlin.model.BreedModel
import com.example.proyecto_tp3_kotlin.model.DoggModel
import javax.inject.Inject

class DoggRespository @Inject constructor(
    private val remote: DoggService,
) {
    suspend fun getDogsByFilter(filters: Map<String, String>): List<DoggModel> {
        return remote.getDogsByFilter(filters)
    }

    suspend fun getAvailableBrands(): List<BreedModel> {
        return remote.getBreeds()
    }
}