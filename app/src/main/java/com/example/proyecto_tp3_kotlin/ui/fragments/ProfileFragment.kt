package com.example.proyecto_tp3_kotlin.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proyecto_tp3_kotlin.R

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bundle = arguments
        val nombre = bundle?.getString("nombre")

        if (nombre != null) {
            println("Nombre: $nombre")
        }else{
            println("No llego el nombre")
        }

        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
}