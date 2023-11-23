package com.example.proyecto_tp3_kotlin.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.proyecto_tp3_kotlin.R
import com.example.proyecto_tp3_kotlin.adapters.AdaptadorPerro
import com.example.proyecto_tp3_kotlin.databinding.FragmentAdopcionBinding
import com.example.proyecto_tp3_kotlin.databinding.FragmentHomeBinding
import com.example.proyecto_tp3_kotlin.listeners.OnPerroClickListener
import com.example.proyecto_tp3_kotlin.model.DogModel
import com.example.proyecto_tp3_kotlin.service.DogDao
import com.example.proyecto_tp3_kotlin.service.DogDataBase
import com.example.proyecto_tp3_kotlin.service.DogRepositoryApi
import com.example.proyecto_tp3_kotlin.service.DogService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AdopcionFragment : Fragment() {
    private lateinit var binding: FragmentAdopcionBinding
    private lateinit var adaptador: AdaptadorPerro
    private var db: DogDataBase? = null
    private var dogDao: DogDao? = null
    var listaPerro = arrayListOf<DogModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdopcionBinding.inflate(inflater, container, false)
        lifecycleScope.launch {
            llenarLista()

        }
        setupRecyclerView()
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)


        binding.buscador.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                setupRecyclerView()
                filtrar(s.toString())
            }

        })
    }

    suspend fun llenarLista() = withContext(Dispatchers.IO) {

        db = DogDataBase.getDatabase(binding.root.context)
        dogDao = db?.dogDao()

        // Obtener la lista de perros después de la inserción
        val perros: List<DogModel>? = dogDao?.getAdoptado()

        // Imprimir la lista de perros en la consola (puedes comentar o eliminar esta línea si no es necesario)
        println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Lista de perros: $perros")

        // Actualizar la lista en el hilo principal
        withContext(Dispatchers.Main) {
            listaPerro.clear()
            if (perros != null) {
                listaPerro.addAll(perros)
                adaptador.notifyDataSetChanged()
                setupRecyclerView()
            }

        }
    }
    private fun setupRecyclerView() {
        binding.rvLista.layoutManager = LinearLayoutManager(requireContext())
        adaptador = AdaptadorPerro(listaPerro, object : OnPerroClickListener {
            override fun onPerroClick(perro: DogModel) {
                val navController = findNavController()

                println("Perro seleccionado: ${perro.name}, Raza: ${perro.breed}, Edad: ${perro.age}")

                // Crear un bundle para pasar datos al fragmento
                val bundle = Bundle()
                bundle.putString("nombre", perro.name)
                bundle.putString("ubicacion", perro.ubication)
                bundle.putString("sexo", perro.gender)
                bundle.putString("dueno", perro.owner)
                bundle.putString("raza", perro.breed)
                if(perro.subBreed == null || perro.subBreed == ""){
                    bundle.putString("subRaza", "N/A")
                } else {
                    bundle.putString("subRaza", perro.subBreed)
                }
                bundle.putInt("edad", perro.age)
                bundle.putInt("peso", perro.weight)
                bundle.putInt("id", perro.id)
                bundle.putBoolean("adoptado", perro.adoptado)
                bundle.putBoolean("favorito", perro.favorito)
                bundle.putString("imageUrl", perro.image)



                navController.navigate(R.id.action_fragment_adopcion_to_detalleFragment, bundle)
                //navController.popBackStack(R.id.fragment_home, false)

            }
            override fun onPerroClickFavorito(perro: DogModel, favoritoButton: ImageButton) {
                agregarFavorito(perro.id)
                // Aquí puedes manejar la lógica específica del botón favorito si es necesario
            }
        })
        binding.rvLista.adapter = adaptador
    }
    fun filtrar(texto: String){
        var listaFiltrada = arrayListOf<DogModel>()

        listaPerro.forEach{
            if(it.breed.toLowerCase().contains(texto.toLowerCase()) || it.subBreed.toLowerCase().contains(texto.toLowerCase()) || it.name.toLowerCase().contains(texto.toLowerCase())){
                listaFiltrada.add(it)
            }
        }
        adaptador.filtrar(listaFiltrada)
    }
    fun agregarFavorito(id: Int){
        db = DogDataBase.getDatabase(binding.root.context)
        dogDao = db?.dogDao()
        lifecycleScope.launch(Dispatchers.IO) {
            dogDao?.favorito(id)
            activity?.runOnUiThread {
                adaptador.notifyDataSetChanged()
                val action = AdopcionFragmentDirections.actionFragmentAdopcionToFragmentFavoritos()
                findNavController().navigate(action)
            }
        }
    }
}