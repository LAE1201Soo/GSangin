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


        val dbHelper = bdAdapter(this)
        clientesList = dbHelper.getClientesList()


        adapter = ClienteAdapter(clientesList)
        recyclerView.adapter = adapter


        adapter.setOnItemClickListener(object : ClienteAdapter.OnItemClickListener {
            override fun onItemClick(cliente: ClienteSQLiteModel) {

                val intent = Intent(this@ListaClientes, Pedido1::class.java)
                intent.putExtra("clienteSeleccionado", cliente)
                startActivity(intent)
            }
        })
    }
}
