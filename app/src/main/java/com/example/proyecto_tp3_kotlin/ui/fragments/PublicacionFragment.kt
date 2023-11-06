package com.example.proyecto_tp3_kotlin.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.proyecto_tp3_kotlin.R
import com.example.proyecto_tp3_kotlin.model.DogModel
import com.example.proyecto_tp3_kotlin.model.DogViewModel
import com.example.proyecto_tp3_kotlin.service.DogRepositoryApi
import com.example.proyecto_tp3_kotlin.service.DogService
import kotlinx.coroutines.launch


class PublicacionFragment : Fragment(

) {




    lateinit var dogName : EditText
    lateinit var dogAge : EditText
    lateinit var dogGender : EditText
    lateinit var dogWeight : EditText
    lateinit var dogBreed : EditText
    lateinit var dogSubBreed : EditText
    lateinit var ownerDetails : EditText
    private lateinit var remote: DogService
    private lateinit var dogRepository: DogRepositoryApi
    lateinit var publicarBoton : Button
    private lateinit var breeds: List<String>

    var i : Int = 0

    private lateinit var dogViewModel : DogViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_publicacion, container, false)



        dogName = view.findViewById(R.id.DogName)
        dogAge =view.findViewById(R.id.DogAge)
        dogGender = view.findViewById(R.id.DogGender)
        dogWeight = view.findViewById(R.id.DogWeight)
        dogBreed = view.findViewById(R.id.DogBreed)
        dogSubBreed = view.findViewById(R.id.DogSubBreed)
        ownerDetails = view.findViewById(R.id.OwnerDetails)

        publicarBoton = view.findViewById(R.id.publicarBoton)

        dogViewModel = ViewModelProvider(this).get(DogViewModel::class.java)


        view.findViewById<Button>(R.id.publicarBoton).setOnClickListener(){
            viewLifecycleOwner.lifecycleScope.launch {
                insertDataToDatabase()
            }
        }

        return view
    }

    suspend fun insertDataToDatabase(){
        remote = DogService()
        dogRepository = DogRepositoryApi(remote)


        val subBreed = dogSubBreed.text.toString()
        val breed = dogBreed.text.toString()
        val name = dogName.text.toString()
        val age = dogAge.text.toString()
        val gender = dogGender.text.toString()
        val weight = dogWeight.text.toString()
        val owner = ownerDetails.text.toString()
        val imagen = dogRepository.getImage(breed, subBreed)




        val dog = DogModel(i, imagen, name, Integer.parseInt(age), gender, Integer.parseInt(weight), breed, subBreed, owner,  "Argentina")
        dogViewModel.addDog(dog)

        i += 1
    }
    suspend fun loadBreeds() {
        remote = DogService()
        dogRepository = DogRepositoryApi(remote)

        try {
            breeds = dogRepository.getAvailableBreeds()


        } catch (e: Exception) {
            // Manejar excepción aquí
            Log.e("Example", e.stackTraceToString())
        }
    }

}