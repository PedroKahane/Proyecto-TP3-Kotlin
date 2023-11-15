package com.example.proyecto_tp3_kotlin.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.proyecto_tp3_kotlin.databinding.FragmentHomeBinding
import com.example.proyecto_tp3_kotlin.model.AdaptadorPerro
import com.example.proyecto_tp3_kotlin.model.DogModel
import com.example.proyecto_tp3_kotlin.service.DogDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adaptador: AdaptadorPerro

    var listaPerro = arrayListOf<DogModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
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
        val db = Room.databaseBuilder(
            requireContext(),
            DogDataBase::class.java, "dog-database"
        ).build()

        val dogdao = db.dogDao()

        // Obtener la lista de perros después de la inserción
        val perros: List<DogModel> = dogdao.getAll()

        // Imprimir la lista de perros en la consola (puedes comentar o eliminar esta línea si no es necesario)
        println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Lista de perros: $perros")

        // Actualizar la lista en el hilo principal
        withContext(Dispatchers.Main) {
            listaPerro.clear()
            listaPerro.addAll(perros)
            adaptador.notifyDataSetChanged()
        }

        // Cerrar la base de datos
        db.close()
    }

    private fun setupRecyclerView() {
    binding.rvLista.layoutManager = LinearLayoutManager(requireContext())
        adaptador = AdaptadorPerro(listaPerro)
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
}