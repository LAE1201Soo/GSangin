package com.example.gsangin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MenuPrincipal : AppCompatActivity() {
    private lateinit var cerrarSesionButton: Button  // Agrega un botón para cerrar sesión

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)

        cerrarSesionButton = findViewById(R.id.btnCerrarSesion)  // Asigna el botón
        cerrarSesionButton.setOnClickListener {
            // Realiza el cierre de sesión
            setLoggedIn(false)  // Establece el estado de autenticación como "no autenticado"
            goToMainActivity()  // Vuelve a la actividad principal (MainActivity)
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
        finish()  // Cierra la actividad actual (MenuPrincipal)
    }
}