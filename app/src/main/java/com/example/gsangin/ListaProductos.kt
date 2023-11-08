package com.example.gsangin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gsangin.model.bdAdapter

class ListaProductos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_productos)

        val recyclerView = findViewById<RecyclerView>(R.id.recliclerProductos)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        // Acceder a la base de datos y obtener los datos de los productos
        val dbHelper = bdAdapter(this)
        val productosList = dbHelper.getProductosList() // Implementa esta función en tu bdAdapter

        // Configurar el adaptador con la lista de productos obtenida
        val adapter = ProductoAdapter(productosList)
        recyclerView.adapter = adapter
    }
}