package com.example.proyecto_tp3_kotlin.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_tp3_kotlin.R

class AdaptadorPerro(
    var listaPerro: ArrayList<DogModel>
): RecyclerView.Adapter<AdaptadorPerro.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val brandNombre = itemView.findViewById(R.id.brandNombre) as TextView
        val brandImagen = itemView.findViewById(R.id.brandImagen) as ImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_perro, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val perro = listaPerro[position]
        holder.brandNombre.text = perro.name

    }

    override fun getItemCount(): Int {
        return listaPerro.size
    }

    fun filtrar(listaFiltrada: ArrayList<DogModel>){
        this.listaPerro = listaFiltrada
        notifyDataSetChanged()
    }
}