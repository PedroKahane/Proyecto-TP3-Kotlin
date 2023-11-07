package com.example.proyecto_tp3_kotlin.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyecto_tp3_kotlin.databinding.ItemDogBinding
import com.example.proyecto_tp3_kotlin.model.Dog

class DogViewHolder (view: View): RecyclerView.ViewHolder(view) {

    //Creo las variables que voy a necesitar para identificar cada elemento

    val binding = ItemDogBinding.bind(view)

    fun render(dogModel: Dog){

        //Voy asignandole el valor a cada una de mis variables

        binding.name.text = dogModel.name
        binding.breed.text  = dogModel.breed
        binding.subBreed.text = dogModel.subBreed
        binding.age.text = dogModel.age.toString()
        Glide.with(binding.photo.context).load(dogModel.photo).into(binding.photo)
    }
}