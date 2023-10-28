package com.example.gsangin

import ClienteAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gsangin.model.bdAdapter

class ListaClientes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_clientes)

        val recyclerView = findViewById<RecyclerView>(R.id.listaClientes)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        // Acceder a la base de datos y obtener los datos de los clientes
        val dbHelper = bdAdapter(this)
        val clientesList = dbHelper.getClientesList() // Implementa esta función en tu bdAdapter

        // Configurar el adaptador con la lista de clientes obtenida
        val adapter = ClienteAdapter(clientesList)
        recyclerView.adapter = adapter
    }
}
