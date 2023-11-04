package com.example.proyecto_tp3_kotlin.ui.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.proyecto_tp3_kotlin.MainActivity
import com.example.proyecto_tp3_kotlin.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var buttonLogin = findViewById<Button>(R.id.button_login)

        buttonLogin.setOnClickListener{

            val intent = Intent(this, MainActivity::class.java);
            startActivity(intent)

        }

    }
}