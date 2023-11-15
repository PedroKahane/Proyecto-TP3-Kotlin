package com.example.proyecto_tp3_kotlin.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.proyecto_tp3_kotlin.R
import com.example.proyecto_tp3_kotlin.service.DogRepositoryApi
import com.example.proyecto_tp3_kotlin.service.DogService
import kotlinx.coroutines.launch


class AdopcionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var adopcionText : TextView
    private lateinit var remote: DogService
    private lateinit var dogRepository: DogRepositoryApi
    private lateinit var breeds: List<String>
    private lateinit var subBreeds: Map<String, List<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_adopcion, container, false)
       /* adopcionText = view.findViewById(R.id.adopcionText)

        adopcionText.text = "Cambie el textxxxxxxxxxxxxxo"
        viewLifecycleOwner.lifecycleScope.launch {
            loadDog()
            val subrazaKelpie = subBreeds["australian"]?.get(0)
            adopcionText.text = subrazaKelpie
            println(subBreeds)
            println(breeds)
        }*/
        return view
    }

    suspend fun loadDog() {
        remote = DogService()
        dogRepository = DogRepositoryApi(remote)

        try {
            breeds = dogRepository.getAvailableBreeds()
            subBreeds = dogRepository.getAvailableSubBreeds()


            /*
            breeds.forEach { breed ->
                println("Raza: $breed")
            }

            subBreeds.forEach { (breed, subBreedsList) ->
                println("Raza: $breed - Subrazas: $subBreedsList")
            }*/

            //val subrazaKelpie = availableSubBreeds["australian"]?.get(0)
            //texto.text = subrazaKelpie


        } catch (e: Exception) {
            // Manejar excepción aquí
            Log.e("Example", e.stackTraceToString())
        }
    }
}