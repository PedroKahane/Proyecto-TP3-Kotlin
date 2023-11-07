package com.example.proyecto_tp3_kotlin.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.proyecto_tp3_kotlin.R
import com.example.proyecto_tp3_kotlin.model.DogModel
import com.example.proyecto_tp3_kotlin.model.DogViewModel
import com.example.proyecto_tp3_kotlin.service.DogRepository
import com.example.proyecto_tp3_kotlin.service.DogRepositoryApi
import com.example.proyecto_tp3_kotlin.service.DogService
import kotlinx.coroutines.launch


class PublicacionFragment : Fragment(

) {




    lateinit var dogName : EditText
    lateinit var dogAge : EditText
    lateinit var dogGender : Spinner
    lateinit var dogWeight : EditText
    lateinit var dogBreed : Spinner
    lateinit var dogSubBreed : Spinner
    lateinit var ownerDetails : EditText
    private lateinit var remote: DogService
    private lateinit var dogRepository: DogRepositoryApi
    lateinit var publicarBoton : Button
    private lateinit var breeds: List<String>
    private lateinit var subBreeds: Map<String, List<String>>
    private lateinit var  selectedItemSpinner : String
    private lateinit var  selectedItemSpinnerSubBreed : String

    var i : Int = 0

    private lateinit var dogViewModel : DogViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_publicacion, container, false)

        dogBreed = view.findViewById(R.id.DogBreed)
        dogSubBreed = view.findViewById(R.id.DogSubBreed)
        dogSubBreed.visibility = View.GONE



        dogName = view.findViewById(R.id.DogName)
        dogAge =view.findViewById(R.id.DogAge)
        dogGender = view.findViewById(R.id.DogGender)
        dogWeight = view.findViewById(R.id.DogWeight)


        ownerDetails = view.findViewById(R.id.OwnerDetails)

        publicarBoton = view.findViewById(R.id.publicarBoton)

        val data = listOf("Seleccionar sexo","Hembra", "Macho")
        val genderAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, data)

        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        dogGender.adapter = genderAdapter

        dogViewModel = ViewModelProvider(this).get(DogViewModel::class.java)
        viewLifecycleOwner.lifecycleScope.launch {
            loadBreeds()

        }
        view.findViewById<Button>(R.id.publicarBoton).setOnClickListener() {
            if(validateInputData(dogBreed.selectedItem.toString(),selectedItemSpinnerSubBreed,dogGender.selectedItem.toString(),dogName.text.toString(), dogAge.text.toString(), dogWeight.text.toString(), ownerDetails.text.toString())) {
                viewLifecycleOwner.lifecycleScope.launch {
                    insertDataToDatabase()

                }
            }
        }

        return view
    }

    suspend fun insertDataToDatabase(){

        remote = DogService()
        dogRepository = DogRepositoryApi(remote)

        var imagen = ""

        val breed = selectedItemSpinner
        val name = dogName.text.toString()
        val age = dogAge.text.toString()
        val gender = dogGender.selectedItem.toString()
        val weight = dogWeight.text.toString()
        val owner = ownerDetails.text.toString()

        println(gender)
        println(breed)
        println(selectedItemSpinnerSubBreed)
        val subBreed = selectedItemSpinnerSubBreed
        if(subBreed != "" && subBreed != null){
            imagen = dogRepository.getImage(breed, subBreed)
        } else {
            imagen = dogRepository.getImageBreed(breed)
        }
        println(imagen)



        val dog = DogModel(i, imagen, name, Integer.parseInt(age), gender, Integer.parseInt(weight), breed, subBreed, owner,  "Argentina")
        dogViewModel.addDog(dog)


        i += 1
    }
    suspend fun loadBreeds() {
        remote = DogService()
        dogRepository = DogRepositoryApi(remote)

        try {
            breeds = dogRepository.getAvailableBreeds()
            subBreeds = dogRepository.getAvailableSubBreeds()

            val breedsConSeleccion = mutableListOf("Seleccionar Raza")
            breedsConSeleccion.addAll(breeds)
            val adapterRaza = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, breedsConSeleccion)

            adapterRaza.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            dogBreed.adapter = adapterRaza

            dogBreed.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                    // Aquí puedes realizar acciones cuando se selecciona un elemento del Spinner
                    val selectedRaza = breedsConSeleccion[position]
                    val subrazas = subBreeds[selectedRaza]


                    selectedItemSpinner = selectedRaza

                    if (subrazas != null && subrazas.isNotEmpty()) {
                        val subBreedsConSeleccion = mutableListOf("Seleccionar Sub Raza")
                        subBreedsConSeleccion.addAll(subrazas)
                        dogSubBreed.visibility = View.VISIBLE
                        val adapterSubraza = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, subBreedsConSeleccion)
                        adapterSubraza.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        dogSubBreed.adapter = adapterSubraza
                        dogSubBreed.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(parent: AdapterView<*>?, view: View?, positionSub: Int, id: Long) {
                                selectedItemSpinnerSubBreed = parent?.getItemAtPosition(positionSub).toString()
                                println(selectedItemSpinnerSubBreed)

                            }
                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                // Se llama cuando no se selecciona ningún elemento
                            }
                        }

                    } else {
                        dogSubBreed.adapter = null
                        selectedItemSpinnerSubBreed = ""
                        dogSubBreed.visibility = View.GONE
                    }
                }


                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Se llama cuando no se selecciona ningún elemento
                }
            }



        } catch (e: Exception) {
            // Manejar excepción aquí
            Log.e("Example", e.stackTraceToString())
        }
    }
    private fun validateInputData(breed: String,subBreed: String,gender: String,name: String, age: String, weight: String, details: String): Boolean{
        val regex = Regex("^[0-9]*\$")
        if (!regex.matches(age)) {
            displayToast("La edad debe ser un numero!")
            return false
        }

        if (!regex.matches(weight)) {
            displayToast("El peso debe ser un numero!")
            return false
        }
        if (gender.equals("Seleccionar sexo")) {
            displayToast("Debes seleccionar un sexo")
            return false
        }

        if (breed.equals("Seleccionar Raza")) {
            displayToast("Debes seleccionar una raza")
            return false
        }
        if (subBreed.equals("Seleccionar Sub Raza")) {
            displayToast("Debes seleccionar una sub raza")
            return false
        }

        if (name.isEmpty()) {
            displayToast("Ingrese nombre, por favor!")
            return false
        }

        if (details.isEmpty()) {
            displayToast("Ingrese el detalle, por favor!")
            return false
        }

        if (age.isEmpty()) {
            displayToast("Ingrese la edad, por favor!")
            return false
        }

        if (weight.isEmpty()) {
            displayToast("Ingrese el peso, por favor!")
            return false
        }
        return true
    }
    private fun displayToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }


}