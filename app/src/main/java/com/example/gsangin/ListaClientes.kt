package com.example.gsangin

import ClienteAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gsangin.model.Pedido1
import com.example.gsangin.model.bdAdapter

class ListaClientes : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ClienteAdapter
    private lateinit var clientesList: List<ClienteSQLiteModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_clientes)

        recyclerView = findViewById(R.id.listaClientes)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        // Acceder a la base de datos y obtener los datos de los clientes
        val dbHelper = bdAdapter(this)
        clientesList = dbHelper.getClientesList()

        // Configurar el adaptador con la lista de clientes obtenida
        adapter = ClienteAdapter(clientesList)
        recyclerView.adapter = adapter

        // Configurar el listener para manejar los clics en los elementos
        adapter.setOnItemClickListener(object : ClienteAdapter.OnItemClickListener {
            override fun onItemClick(cliente: ClienteSQLiteModel) {
                // Aquí debes iniciar la nueva actividad pasando la información completa del cliente
                val intent = Intent(this@ListaClientes, Pedido1::class.java)
                intent.putExtra("clienteSeleccionado", cliente)
                startActivity(intent)
            }
        })
    }
}
