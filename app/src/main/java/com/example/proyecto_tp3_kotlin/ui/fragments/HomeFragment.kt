package com.example.proyecto_tp3_kotlin.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.proyecto_tp3_kotlin.databinding.FragmentHomeBinding
import com.example.proyecto_tp3_kotlin.listeners.OnPerroClickListener
import com.example.proyecto_tp3_kotlin.model.AdaptadorPerro
import com.example.proyecto_tp3_kotlin.model.DogModel
import com.example.proyecto_tp3_kotlin.service.DogDao
import com.example.proyecto_tp3_kotlin.service.DogDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.proyecto_tp3_kotlin.R

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adaptador: AdaptadorPerro
    private var db: DogDataBase? = null
    private var dogDao: DogDao? = null

    var listaPerro = arrayListOf<DogModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        lifecycleScope.launch {
            llenarLista()
        }
        setupRecyclerView()
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
                lifecycleScope.launch {
                    llenarLista()
                }
                setupRecyclerView()
                filtrar(s.toString())
            }

        })
    }

    suspend fun llenarLista() = withContext(Dispatchers.IO) {

        db = DogDataBase.getDatabase(binding.root.context)
        dogDao = db?.dogDao()

        // Obtener la lista de perros después de la inserción
        val perros: List<DogModel>? = dogDao?.getAll()

        // Imprimir la lista de perros en la consola (puedes comentar o eliminar esta línea si no es necesario)
        println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Lista de perros: $perros")

        // Actualizar la lista en el hilo principal
        withContext(Dispatchers.Main) {
            listaPerro.clear()
            if (perros != null) {
                listaPerro.addAll(perros)
            }
            adaptador.notifyDataSetChanged()
        }
    }

    private fun setupRecyclerView() {
    binding.rvLista.layoutManager = LinearLayoutManager(requireContext())
        adaptador = AdaptadorPerro(listaPerro, object : OnPerroClickListener {
            override fun onPerroClick(perro: DogModel) {
                // Aquí puedes manejar el clic del perro en el fragmento
                println("Perro seleccionado: ${perro.name}, Raza: ${perro.breed}, Edad: ${perro.age}")
                val fragmentManager = (binding.root.context as AppCompatActivity).supportFragmentManager
                val detalleFragment = DetalleFragment()

                // Crear un bundle para pasar datos al fragmento
                val bundle = Bundle()
                bundle.putString("nombre", perro.name)
                bundle.putString("ubicacion", perro.ubication)
                bundle.putString("sexo", perro.gender)
                bundle.putString("dueno", perro.owner)
                bundle.putInt("edad", perro.age)
                bundle.putInt("peso", perro.weight)
                detalleFragment.arguments = bundle

                // Abrir el fragmento
                fragmentManager.beginTransaction()
                    .replace(R.id.fragment_home, detalleFragment)
                    .addToBackStack(null)
                    .commit()
            }
        })

        binding.rvLista.adapter = adaptador
    }

    fun filtrar(texto: String){
        var listaFiltrada = arrayListOf<DogModel>()

        listaPerro.forEach{
            if(it.name.toLowerCase().contains(texto.toLowerCase())){
                listaFiltrada.add(it)
            }
        }
        adaptador.filtrar(listaFiltrada)
    }
    /*override fun onPerroClick(perro: DogModel) {
        // Aquí puedes imprimir por consola los datos del perro desde el fragmento
        println("Perro seleccionado: ${perro.name}, Raza: ${perro.breed}, Edad: ${perro.age}")
    }*/
}