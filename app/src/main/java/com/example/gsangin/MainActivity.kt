package com.example.gsangin

import android.content.Intent
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
    private lateinit var txtRegis: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtRegis = findViewById(R.id.crearUser)
        userName = findViewById(R.id.txtUser)
        Contraseñatxt = findViewById(R.id.txtContraseña)
        Ingresartxt = findViewById(R.id.btnIngresar)
        Ingresartxt.setOnClickListener {
            val username = userName.text.toString()
            val password = Contraseñatxt.text.toString()


            if (username == "Kyung1201" && password == "Exo0t09D") {

                Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()

            } else {

                Toast.makeText(this, "Usuario o Contraseña Incorrecta", Toast.LENGTH_SHORT).show()
            }

            txtRegis.setOnClickListener {
                // Agregar aquí el código para abrir la segunda actividad
                val intent = Intent(this, RegiUser::class.java)
                startActivity(intent)
            }
    }

    }}
