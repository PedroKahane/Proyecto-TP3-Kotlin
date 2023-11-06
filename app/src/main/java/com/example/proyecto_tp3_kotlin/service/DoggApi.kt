package com.example.proyecto_tp3_kotlin.service


import com.example.proyecto_tp3_kotlin.model.BreedModel
import com.example.proyecto_tp3_kotlin.model.DogModel

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap
interface DoggApi {

    @GET("api/breeds/list/all")
    suspend fun getAvailableBreeds(
    ): Response<List<BreedModel>>
    @GET("api/breeds/list/all")
    suspend fun getDogsByFilter(
        @QueryMap filters: Map<String, String>
    ): Response<List<DogModel>>
}