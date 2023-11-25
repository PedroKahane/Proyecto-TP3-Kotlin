package com.example.proyecto_tp3_kotlin.ui.views

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.proyecto_tp3_kotlin.MainActivity
import com.example.proyecto_tp3_kotlin.R
import com.example.proyecto_tp3_kotlin.databinding.ActivityLoginBinding
import com.example.proyecto_tp3_kotlin.databinding.ActivityMainBinding
import com.example.proyecto_tp3_kotlin.preferences.AppPreferences
import com.example.proyecto_tp3_kotlin.service.DogDataBase
import com.example.proyecto_tp3_kotlin.ui.fragments.ProfileFragment
import com.example.proyecto_tp3_kotlin.viewmodels.SharedViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        DogDataBase.insertDatabase(binding.root.context)

        var buttonLogin = findViewById<Button>(R.id.button_login)

        buttonLogin.setOnClickListener {
            if (validarNumero() && validarNombre()) {
                val nombre = binding.name.editableText.toString()
                val bundle = Bundle()
                bundle.putString("nombre", nombre)

                val sharedPreferences = getSharedPreferences("nombre_prefs", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("nombre", nombre)
                editor.apply()

                val intent = Intent(this, MainActivity::class.java);
                startActivity(intent)
            } else {
                validate()
            }
        }

    }

    private fun validarNombre() : Boolean {
        var isvalid = true
        val nombre = binding.name.editableText.toString()

        if(nombre.isEmpty()){
            binding.name.error = "Pone un nombre valido"
            isvalid = false
        }
        return isvalid
    }

    private fun validarNumero() : Boolean {
        var isvalid = true
        val telefono = binding.phone.editableText.toString()
        if(telefono.isEmpty()){
            binding.phone.error = "Pone un Telefono valido"
            isvalid = false
        }
        return isvalid
    }

    private fun validate(){
        val result = arrayOf(validarNombre(), validarNumero())

        if(false in result){
            return
        }
        Toast.makeText(this,"Success", Toast.LENGTH_SHORT).show()
    }
}