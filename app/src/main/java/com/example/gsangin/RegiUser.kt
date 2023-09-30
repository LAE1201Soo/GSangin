package com.example.gsangin

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class RegiUser : AppCompatActivity() {
    private lateinit var nombreEditText: EditText
    private lateinit var usuarioEditText: EditText
    private lateinit var contraseñaEditText: EditText
    private lateinit var confirmarContraseñaEditText: EditText
    private lateinit var guardarButton: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regi_user)
        sharedPreferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE)

        nombreEditText = findViewById(R.id.txtNombre)
        usuarioEditText = findViewById(R.id.txtUsuario)
        contraseñaEditText = findViewById(R.id.txtPass)
        confirmarContraseñaEditText = findViewById(R.id.txtPassCom)
        guardarButton = findViewById(R.id.btnGuardar)

        guardarButton.setOnClickListener {
            val nombre = nombreEditText.text.toString()
            val usuario = usuarioEditText.text.toString()
            val contraseña = contraseñaEditText.text.toString()
            val confirmarContraseña = confirmarContraseñaEditText.text.toString()

            // Verificar que la contraseña y la confirmación de contraseña sean iguales
            if (contraseña == confirmarContraseña) {
                // Las contraseñas coinciden, guardar los datos en las preferencias compartidas
                val editor = sharedPreferences.edit()
                editor.putString("nombre", nombre)
                editor.putString("usuario", usuario)
                editor.putString("contraseña", contraseña)
                editor.apply()

                Toast.makeText(this, "Datos guardados", Toast.LENGTH_SHORT).show()

                // Limpiar los campos
                nombreEditText.text.clear()
                usuarioEditText.text.clear()
                contraseñaEditText.text.clear()
                confirmarContraseñaEditText.text.clear()
            } else {
                // Las contraseñas no coinciden, mostrar un mensaje de error
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            }
        }
    }
        }
