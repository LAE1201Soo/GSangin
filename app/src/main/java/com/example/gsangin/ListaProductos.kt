package com.example.gsangin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gsangin.model.ProductoSQLiteModel
import com.example.gsangin.model.bdAdapter

class ListaProductos : AppCompatActivity(), ProductoAdapter.ProductoClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductoAdapter
    private lateinit var productosList: List<ProductoSQLiteModel>
    private lateinit var productosFiltrados: List<ProductoSQLiteModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_productos)

        recyclerView = findViewById(R.id.recliclerProductos)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val dbHelper = bdAdapter(this)
        productosList = dbHelper.getProductosList()


        productosFiltrados = productosList


        adapter = ProductoAdapter(productosFiltrados, this)
        recyclerView.adapter = adapter

        val editTextSearch = findViewById<EditText>(R.id.txtBuscar)

        editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim()


                productosFiltrados = productosList.filter { producto ->
                    producto.nombre.contains(query, ignoreCase = true)
                }


                adapter.updateList(productosFiltrados)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }


    override fun onProductoClick(producto: ProductoSQLiteModel) {

        Toast.makeText(this, "Producto clicado: ${producto.nombre}", Toast.LENGTH_SHORT).show()
    }
}
