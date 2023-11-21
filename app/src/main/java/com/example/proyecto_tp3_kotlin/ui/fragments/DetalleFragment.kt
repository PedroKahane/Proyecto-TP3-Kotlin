package com.example.proyecto_tp3_kotlin.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.proyecto_tp3_kotlin.R
import com.example.proyecto_tp3_kotlin.adapters.AdaptadorPerro
import com.example.proyecto_tp3_kotlin.service.DogDao
import com.example.proyecto_tp3_kotlin.service.DogDataBase
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetalleFragment : Fragment()
{   lateinit var v : View
    private var db: DogDataBase? = null
    private var dogDao: DogDao? = null
    private lateinit var adaptador: AdaptadorPerro
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_detalle, container, false)

        // Obtener datos del perro de los argumentos
        val nombre = arguments?.getString("nombre")
        val ubicacion = arguments?.getString("ubicacion")
        val sexo = arguments?.getString("sexo")
        val dueno = arguments?.getString("dueno")
        val edad = arguments?.getInt("edad", 0) ?: 0
        val peso = arguments?.getInt("peso", 0) ?: 0
        val id = arguments?.getInt("id") ?: 0
        val adoptado = arguments?.getBoolean("adoptado") ?: false
        val imageUrl = arguments?.getString("imageUrl")

        // Configurar las vistas con los datos del perro
        v.findViewById<TextView>(R.id.Nombre).text = nombre
        v.findViewById<TextView>(R.id.Ubicacion).text = ubicacion
        v.findViewById<TextView>(R.id.NombreDelDueño).text = dueno
        v.findViewById<TextView>(R.id.EdadNumero).text = edad.toString()
        v.findViewById<TextView>(R.id.SexPerro).text = sexo
        v.findViewById<TextView>(R.id.PesoPerro).text = peso.toString() + "Kg"
        val imageView = v.findViewById<ImageView>(R.id.imageView)
        Picasso.get().load(imageUrl).into(imageView)


        v.findViewById<Button>(R.id.Adoptar).setOnClickListener() {
            if(validate(adoptado)) {
                viewLifecycleOwner.lifecycleScope.launch {
                    adoptar(id)

                }
            }
        }
        return v
    }
    suspend fun adoptar(id: Int){
        db = DogDataBase.getDatabase(v.context)
        dogDao = db?.dogDao()
        lifecycleScope.launch(Dispatchers.IO) {
            dogDao?.Adoptar(id)
            activity?.runOnUiThread {
                val action = DetalleFragmentDirections.actionDetalleFragmentToFragmentAdopcion()
                findNavController().navigate(action)
            }
        }
    }
    private fun displayToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
    private fun validate(adoptado: Boolean): Boolean{
        println(adoptado)
        if(adoptado == true){
            displayToast("Este perro ya fue adoptado!")
            return false
        }
        return true
    }
}