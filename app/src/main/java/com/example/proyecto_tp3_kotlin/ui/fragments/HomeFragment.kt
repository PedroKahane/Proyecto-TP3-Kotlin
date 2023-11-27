package com.example.proyecto_tp3_kotlin.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyecto_tp3_kotlin.databinding.FragmentHomeBinding
import com.example.proyecto_tp3_kotlin.listeners.OnPerroClickListener
import com.example.proyecto_tp3_kotlin.adapters.AdaptadorPerro
import com.example.proyecto_tp3_kotlin.model.DogModel
import com.example.proyecto_tp3_kotlin.service.DogDao
import com.example.proyecto_tp3_kotlin.service.DogDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.proyecto_tp3_kotlin.R
import com.example.proyecto_tp3_kotlin.databinding.ItemRvPerroBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var adaptador: AdaptadorPerro? = null
    private var db: DogDataBase? = null
    private var dogDao: DogDao? = null
    val favorito = arguments?.getBoolean("favorito") ?: false

    var listaPerro = arrayListOf<DogModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        lifecycleScope.launch {
            llenarLista()
            setupRecyclerView()
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)


        binding.buscador.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                filtrar(s.toString())
            }

        })
    }

    suspend fun llenarLista() = withContext(Dispatchers.IO) {
        db = DogDataBase.getDatabase(binding.root.context)


        dogDao = db?.dogDao()

        val perros: List<DogModel>? = dogDao?.getAll()

        withContext(Dispatchers.Main) {
            listaPerro.clear()
            if (perros != null) {
                listaPerro.addAll(perros)
            }
        }
    }

    private fun setupRecyclerView() {

        binding.rvLista.layoutManager = LinearLayoutManager(requireContext())
        adaptador = AdaptadorPerro(listaPerro, object : OnPerroClickListener {
            override fun onPerroClick(perro: DogModel) {
                val navController = findNavController()

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

                navController.navigate(R.id.action_fragment_home_to_detalleFragment, bundle)
            }

            override fun onPerroClickFavorito(perro: DogModel, favoritoButton: ImageButton) {
                agregarFavorito(perro.id)

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
        adaptador?.filtrar(listaFiltrada)
    }

    fun agregarFavorito(id: Int){
        db = DogDataBase.getDatabase(binding.root.context)
        dogDao = db?.dogDao()
        lifecycleScope.launch(Dispatchers.IO) {
            dogDao?.favorito(id)
            activity?.runOnUiThread {
                adaptador?.notifyDataSetChanged()
                val action = HomeFragmentDirections.actionFragmentHomeToFragmentFavoritos()
                findNavController().navigate(action)
            }
        }
    }


}