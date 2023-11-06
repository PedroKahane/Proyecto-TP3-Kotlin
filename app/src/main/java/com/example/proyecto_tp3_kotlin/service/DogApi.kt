package com.example.proyecto_tp3_kotlin.service


import com.example.proyecto_tp3_kotlin.model.BreedModel
import com.example.proyecto_tp3_kotlin.model.BreedsResponse
import com.example.proyecto_tp3_kotlin.model.DogModel
import com.example.proyecto_tp3_kotlin.model.ImageResponse

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
interface DogApi {

    @GET("api/breeds/list/all")
    suspend fun getAvailableBreeds(
    ): BreedsResponse
    @GET("api/breeds/list/all")
    suspend fun getDogsByFilter(
        @QueryMap filters: Map<String, String>
    ): Response<List<DogModel>>
    @GET("api/breed/{breed}/images/random")
    suspend fun getBreedDogImage(@Path("breed") breed: String): ImageResponse
    @GET("api/breed/{breed}/{subbreed}/images/random")
    suspend fun getSubBreedDogImage(@Path("breed") breed: String, @Path("subBreed") subBreed: String): ImageResponse
}