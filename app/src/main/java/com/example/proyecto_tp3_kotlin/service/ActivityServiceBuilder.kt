package com.example.proyecto_tp3_kotlin.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object ActivityServiceBuilder {

    private val BASE_URL = "https://dog.ceo/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun create(): DoggApi {
        return retrofit.create(DoggApi::class.java)
    }
}