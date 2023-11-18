package com.example.proyecto_tp3_kotlin.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.proyecto_tp3_kotlin.R

class DetalleFragment : Fragment()
{
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detalle, container, false)

        // Obtener datos del perro de los argumentos
        val nombre = arguments?.getString("nombre")
        val ubicacion = arguments?.getString("ubicacion")
        val sexo = arguments?.getString("sexo")
        val dueno = arguments?.getString("dueno")
        val edad = arguments?.getInt("edad", 0) ?: 0
        val peso = arguments?.getInt("peso", 0) ?: 0

        // Configurar las vistas con los datos del perro
        view.findViewById<TextView>(R.id.Nombre).text = nombre
        view.findViewById<TextView>(R.id.Ubicacion).text = ubicacion
        view.findViewById<TextView>(R.id.NombreDelDueño).text = dueno
        view.findViewById<TextView>(R.id.EdadNumero).text = edad.toString()
        view.findViewById<TextView>(R.id.SexPerro).text = sexo
        view.findViewById<TextView>(R.id.PesoPerro).text = peso.toString() + "Kg"


        return view
    }
}