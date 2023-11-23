package com.example.proyecto_tp3_kotlin.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
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
        val raza = arguments?.getString("raza")
        val subRaza = arguments?.getString("subRaza", "N/A")
        val id = arguments?.getInt("id") ?: 0
        val adoptado = arguments?.getBoolean("adoptado") ?: false
        val favorito = arguments?.getBoolean("favorito") ?: false
        val imageUrl = arguments?.getString("imageUrl")

        // Configurar las vistas con los datos del perro
        v.findViewById<TextView>(R.id.Nombre).text = nombre
        v.findViewById<TextView>(R.id.Ubicacion).text = ubicacion
        v.findViewById<TextView>(R.id.NombreDelDueño).text = dueno
        v.findViewById<TextView>(R.id.EdadNumero).text = edad.toString()
        v.findViewById<TextView>(R.id.SexPerro).text = sexo
        v.findViewById<TextView>(R.id.PesoPerro).text = peso.toString() + "Kg"
        v.findViewById<TextView>(R.id.RazaPerro).text = raza
        v.findViewById<TextView>(R.id.SubRazaPerro).text = subRaza
        val imageView = v.findViewById<ImageView>(R.id.imageView)
        Picasso.get().load(imageUrl).into(imageView)
        v.findViewById<ImageButton>(R.id.imageView4).setOnClickListener() {
            val intent = Intent(Intent.ACTION_DIAL)

            if (intent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(intent)
            } else {
                // Manejar el caso en el que no hay aplicación de llamadas disponible
                Toast.makeText(requireContext(), "No se encontró una aplicación de llamadas", Toast.LENGTH_SHORT).show()
            }
        }
        if(favorito){
            v.findViewById<ImageButton>(R.id.favorito).setImageResource(R.drawable.guardado)
        }
        if(!favorito){
            v.findViewById<ImageButton>(R.id.favorito).setImageResource(R.drawable.sin_guardar)
        }
        v.findViewById<Button>(R.id.Adoptar).setOnClickListener() {
            if(validate(adoptado)) {
                viewLifecycleOwner.lifecycleScope.launch {
                    adoptar(id)

                }
            }
        }
        v.findViewById<ImageButton>(R.id.favorito).setOnClickListener() {
                viewLifecycleOwner.lifecycleScope.launch {
                    agregarFavorito(id, nombre, ubicacion, sexo, dueno, edad, peso, raza, subRaza, adoptado, favorito, imageUrl)
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
    fun agregarFavorito(id: Int, nombre: String?, ubicacion: String?, sexo: String?, dueno: String?, edad: Int, peso: Int, raza: String?, subRaza: String?, adoptado: Boolean,favorito: Boolean, imageUrl: String?){
        db = DogDataBase.getDatabase(v.context)
        dogDao = db?.dogDao()
        lifecycleScope.launch(Dispatchers.IO) {
            dogDao?.favorito(id)
            activity?.runOnUiThread {
                val navController = findNavController()
                // Crear un bundle para pasar datos al fragmento
                val bundle = Bundle()
                bundle.putString("nombre", nombre)
                bundle.putString("ubicacion", ubicacion)
                bundle.putString("sexo", sexo)
                bundle.putString("dueno", dueno)
                bundle.putString("raza", raza)
                if(subRaza == null || subRaza == ""){
                    bundle.putString("subRaza", "N/A")
                } else {
                    bundle.putString("subRaza", subRaza)
                }
                bundle.putInt("edad", edad)
                bundle.putInt("peso", peso)
                bundle.putInt("id", id)
                bundle.putBoolean("adoptado", adoptado)
                bundle.putBoolean("favorito", !favorito)
                bundle.putString("imageUrl", imageUrl)
                navController.navigate(R.id.action_detalleFragment_self, bundle)
            }
        }
    }
}