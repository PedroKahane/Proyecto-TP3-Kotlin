package com.example.proyecto_tp3_kotlin.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_tp3_kotlin.R
import com.example.proyecto_tp3_kotlin.listeners.OnPerroClickListener

class AdaptadorPerro(
    var listaPerro: ArrayList<DogModel>,
    private val listener: OnPerroClickListener
): RecyclerView.Adapter<AdaptadorPerro.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val brandNombre = itemView.findViewById(R.id.brandNombre) as TextView
        val brandImagen = itemView.findViewById(R.id.brandImagen) as ImageView
        val brandEdad = itemView.findViewById(R.id.brandEdad) as TextView
        val brandRaza = itemView.findViewById(R.id.brandRaza) as TextView
        val brandSubRaza = itemView.findViewById(R.id.brandSubRaza) as TextView
        val brandGenero = itemView.findViewById(R.id.brandGenero) as TextView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_perro, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val perro = listaPerro[position]
        holder.brandNombre.text = perro.name
        holder.brandEdad.text = perro.age.toString()
        holder.brandRaza.text = perro.breed

        if (perro.subBreed.isEmpty()){
            holder.brandSubRaza.text = "Sin SubRaza"
        }else{
            holder.brandSubRaza.text = perro.subBreed
        }

        holder.brandGenero.text = perro.gender
        val imagenDrawable = ContextCompat.getDrawable(holder.itemView.context, R.drawable.imagen_login_perro)
        holder.brandImagen.setImageDrawable(imagenDrawable)
        holder.itemView.setOnClickListener(){
            listener.onPerroClick(perro)
        }

    }

    override fun getItemCount(): Int {
        return listaPerro.size
    }

    fun filtrar(listaFiltrada: ArrayList<DogModel>){
        this.listaPerro = listaFiltrada
        notifyDataSetChanged()
    }
}