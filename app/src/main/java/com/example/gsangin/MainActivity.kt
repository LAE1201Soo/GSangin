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
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken

import com.google.gson.Gson


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

        // Verificar si el usuario ya está autenticado
        if (isLoggedIn()) {
            goToMainMenu()
        }

        Ingresartxt.setOnClickListener {
            val username = userName.text.toString()
            val password = Contraseñatxt.text.toString()

            val usersList = getUsersList()

            val user = usersList.find { it.usuario == username }

            if (user != null && user.contraseña == password) {
                Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()

                // Guardar el estado de autenticación
                setLoggedIn(true)

                goToMainMenu()
            } else {
                Toast.makeText(this, "Usuario o Contraseña Incorrecta", Toast.LENGTH_SHORT).show()
            }
        }

        btnAbrirSegundaActividad.setOnClickListener {
            val intent = Intent(this, RegiUser::class.java)
            startActivity(intent)
        }
    }

    private fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    private fun setLoggedIn(loggedIn: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", loggedIn)
        editor.apply()
    }

    private fun getUsersList(): MutableList<User> {
        val json = sharedPreferences.getString("usersList", "[]")
        val type = object : TypeToken<MutableList<User>>() {}.type
        return Gson().fromJson(json, type) ?: mutableListOf()
    }

    private fun goToMainMenu() {
        val intent = Intent(this, MenuPrincipal::class.java)
        startActivity(intent)
        finish() // Evitar que el usuario regrese a esta actividad con el botón "Atrás"
    }
}