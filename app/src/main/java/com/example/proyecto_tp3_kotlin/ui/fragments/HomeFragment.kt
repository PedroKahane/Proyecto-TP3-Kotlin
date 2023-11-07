package com.example.proyecto_tp3_kotlin.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.text.toLowerCase
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_tp3_kotlin.databinding.ActivityMainBinding
import com.example.proyecto_tp3_kotlin.databinding.FragmentHomeBinding
import com.example.proyecto_tp3_kotlin.model.AdaptadorPerro
import com.example.proyecto_tp3_kotlin.model.Perro

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adaptador: AdaptadorPerro

    var listaPerro = arrayListOf<Perro>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        llenarLista()
        setupRecyclerView()

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

    fun llenarLista(){
        listaPerro.add(Perro("pedro"))
        listaPerro.add(Perro("santy"))
        listaPerro.add(Perro("carlos"))
        listaPerro.add(Perro("mati"))
    }

    private fun setupRecyclerView() {
    binding.rvLista.layoutManager = LinearLayoutManager(requireContext())
        adaptador = AdaptadorPerro(listaPerro)
        binding.rvLista.adapter = adaptador
    }

    fun filtrar(texto: String){
        var listaFiltrada = arrayListOf<Perro>()

        listaPerro.forEach{
            if(it.nombre.toLowerCase().contains(texto.toLowerCase())){
                listaFiltrada.add(it)
            }
        }
        adaptador.filtrar(listaFiltrada)
    }
}