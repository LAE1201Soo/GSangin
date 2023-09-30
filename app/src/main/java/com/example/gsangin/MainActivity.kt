package com.example.gsangin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var userName: EditText
    private lateinit var Contraseñatxt: EditText
    private lateinit var Ingresartxt: Button
    private lateinit var btnAbrirSegundaActividad: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE)

        userName = findViewById(R.id.txtUser)
        Contraseñatxt = findViewById(R.id.txtContraseña)
        Ingresartxt = findViewById(R.id.btnIngresar)
        btnAbrirSegundaActividad = findViewById(R.id.btnRegis)

        Ingresartxt.setOnClickListener {
            val username = userName.text.toString()
            val password = Contraseñatxt.text.toString()

            val savedUsername = sharedPreferences.getString("usuario", "")
            val savedPassword = sharedPreferences.getString("contraseña", "")

            if (username == savedUsername && password == savedPassword) {
                Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MenuPrincipal::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Usuario o Contraseña Incorrecta", Toast.LENGTH_SHORT).show()
            }
        }

        btnAbrirSegundaActividad.setOnClickListener {
            val intent = Intent(this, RegiUser::class.java)
            startActivity(intent)
        }
    }}
