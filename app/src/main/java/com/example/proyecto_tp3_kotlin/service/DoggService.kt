package com.example.proyecto_tp3_kotlin.service

import com.example.proyecto_tp3_kotlin.model.BreedModel
import com.example.proyecto_tp3_kotlin.model.DoggModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
class DoggService @Inject constructor(
    private val api: DoggApi
) {
    suspend fun getDogsByFilter(filters: Map<String, String>): List<DoggModel> {
        return withContext(Dispatchers.IO) {
            val response = api.getDogsByFilter(filters)
            response.body() ?: emptyList()
        }
    }
    suspend fun getBreeds(): List<BreedModel> {
        return withContext(Dispatchers.IO) {
            val response = api.getAvailableBreeds()
            response.body() ?: emptyList()
        }
    }
}