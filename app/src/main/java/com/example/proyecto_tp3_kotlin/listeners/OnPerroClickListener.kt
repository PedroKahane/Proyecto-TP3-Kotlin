package com.example.proyecto_tp3_kotlin.listeners

import android.widget.ImageButton
import com.example.proyecto_tp3_kotlin.model.DogModel

interface OnPerroClickListener {
    fun onPerroClick(perro: DogModel)
    fun onPerroClickFavorito(perro: DogModel, favoritoButton: ImageButton)
}