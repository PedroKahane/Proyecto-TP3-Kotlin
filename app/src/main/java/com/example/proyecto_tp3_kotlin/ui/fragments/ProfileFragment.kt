package com.example.proyecto_tp3_kotlin.ui.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.proyecto_tp3_kotlin.databinding.FragmentProfileBinding
import java.io.File
import java.io.FileOutputStream

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        val btnCamara = binding.btnCamara
        val nombrePerfil = binding.nombrePerfil

        val sharedPreferences = requireActivity().getSharedPreferences("nombre_prefs", Context.MODE_PRIVATE)
        val nombre = sharedPreferences.getString("nombre", "ValorPredeterminado")

        nombrePerfil.setText(nombre)

        cargarImagen()

        btnCamara.setOnClickListener {
            abrirCamara()
        }

        return binding.root
    }

    private fun abrirCamara() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val imagenPerfil = binding.imagenPerfil
            val imageBitmap = data?.extras?.get("data") as Bitmap
            guardarImagen(imageBitmap)
            imagenPerfil.setImageBitmap(imageBitmap)
        }
    }

    private fun guardarImagen(bitmap: Bitmap) {
        val file = File(requireContext().filesDir, "imagen_perfil.jpg")
        val stream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        stream.flush()
        stream.close()
    }

    private fun cargarImagen() {
        val file = File(requireContext().filesDir, "imagen_perfil.jpg")
        if (file.exists()) {
            val bitmap = BitmapFactory.decodeFile(file.absolutePath)
            binding.imagenPerfil.setImageBitmap(bitmap)
        }
    }
}