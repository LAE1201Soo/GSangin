package com.example.gsangin

import android.content.Context

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson

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

            // Verificar si algún campo está vacío
            if (nombre.isEmpty() || usuario.isEmpty() || contraseña.isEmpty() || confirmarContraseña.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            } else if (usuario.length < 4) {
                Toast.makeText(this, "El nombre de usuario debe tener al menos 4 caracteres", Toast.LENGTH_SHORT).show()
            } else if (contraseña.length < 8) {
                Toast.makeText(this, "La contraseña debe tener al menos 8 caracteres", Toast.LENGTH_SHORT).show()
            } else if (contraseña == confirmarContraseña) {
                val usersList = getUsersList()

                if (isUsernameTaken(usersList, usuario)) {
                    Toast.makeText(this, "El nombre de usuario ya está en uso", Toast.LENGTH_SHORT).show()
                } else {
                    val newUser = User(nombre, usuario, contraseña)
                    usersList.add(newUser)
                    saveUsersList(usersList)

                    Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show()


                    nombreEditText.text.clear()
                    usuarioEditText.text.clear()
                    contraseñaEditText.text.clear()
                    confirmarContraseñaEditText.text.clear()

                    finish()
                }
            } else {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getUsersList(): MutableList<User> {
        val json = sharedPreferences.getString("usersList", "[]")
        val type = object : TypeToken<MutableList<User>>() {}.type
        return Gson().fromJson(json, type) ?: mutableListOf()
    }

    private fun saveUsersList(usersList: MutableList<User>) {
        val json = Gson().toJson(usersList)
        val editor = sharedPreferences.edit()
        editor.putString("usersList", json)
        editor.apply()
    }

    private fun isUsernameTaken(usersList: MutableList<User>, username: String): Boolean {
        return usersList.any { it.usuario == username }
    }
}
