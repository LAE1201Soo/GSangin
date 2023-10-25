package com.example.gsangin

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gsangin.model.ClientesObj
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class Sincronizar : AppCompatActivity() {
    private lateinit var btnSincronizar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sincronizar)

        btnSincronizar = findViewById(R.id.btnSincronizarD)

        btnSincronizar.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val clientes = ClientesObj.service.listClientesData().execute()

                    if (clientes.isSuccessful) {
                        val bodyClientes = clientes.body()
                        if (bodyClientes != null) {
                            for (cliente in bodyClientes) {
                                Log.d("Sicronizar", "Nombre de cliente: ${cliente.nombre}")
                                Log.d("Sicronizar", "Razon social: ${cliente.razon_social}")
                            }
                            runOnUiThread {
                                Toast.makeText(this@Sincronizar, "Conexión exitosa", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Log.e("Sicronizar", "Error en la solicitud HTTP: ${clientes.code()} ${clientes.message()}")
                    }
                } catch (e: Exception) {
                    Log.e("Sincronizar", "Error al sincronizar: ${e.message}", e)
                    runOnUiThread {
                        Toast.makeText(this@Sincronizar, "Error al sincronizar: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
