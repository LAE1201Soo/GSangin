package com.example.gsangin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class MenuPrincipal : AppCompatActivity() {
    private lateinit var cerrarSesionButton: Button
    private lateinit var imgSincronizaButton: ImageButton
    private lateinit var imgVentas: ImageButton
    private lateinit var imagPedidos: ImageButton
    private lateinit var imgInve: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)



        cerrarSesionButton = findViewById(R.id.btnCerrarSesion)
        imgSincronizaButton = findViewById(R.id.imgSincroniza)
        imgVentas= findViewById(R.id.imgventas)
        imagPedidos= findViewById(R.id.imgPedidos)
        imgInve= findViewById(R.id.imgInventario)
        cerrarSesionButton.setOnClickListener {

            setLoggedIn(false)
            goToMainActivity()
        }
    
        imgSincronizaButton.setOnClickListener {

            val intent = Intent(this, Sincronizar::class.java)
            startActivity(intent)
        }
        imgVentas.setOnClickListener {

            val intent = Intent(this, ListaClientes::class.java)
            startActivity(intent)
        }
        imagPedidos.setOnClickListener {

            val intent = Intent(this, Temporal::class.java)
            startActivity(intent)
        }
        imgInve.setOnClickListener {

            val intent = Intent(this, ListaProductos::class.java)
            startActivity(intent)
        }
    }

    private fun setLoggedIn(loggedIn: Boolean) {
        val sharedPreferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", loggedIn)
        editor.apply()
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}