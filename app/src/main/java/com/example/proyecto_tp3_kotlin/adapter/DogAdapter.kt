package com.example.proyecto_tp3_kotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_tp3_kotlin.R
import com.example.proyecto_tp3_kotlin.model.Dog
import com.example.proyecto_tp3_kotlin.model.DogProvider
import com.example.proyecto_tp3_kotlin.model.DogProvider.Companion.dogList

class DogAdapter (private val superheroList: List<Dog>) :  RecyclerView.Adapter<DogViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        //Devuelve al viewHolder los items
        val layoutInflater = LayoutInflater.from(parent.context)
        return DogViewHolder(layoutInflater.inflate(R.layout.item_dog, parent,  false))
    }

    override fun getItemCount(): Int {
        //Devuelve el tamaño del listado
        return dogList.size

    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        //Devuelve la instancia del viewHolder y la posición en la que estamos
        val item = dogList[position]
        holder.render(item)
    }

}