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

        // Inicializa la lista filtrada con la lista original al principio
        productosFiltrados = productosList

        // Pasa la instancia actual de la actividad como listener al adaptador
        adapter = ProductoAdapter(productosFiltrados, this)
        recyclerView.adapter = adapter

        val editTextSearch = findViewById<EditText>(R.id.txtBuscar)

        editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim()

                // Filtra la lista de productos basados en el nombre ingresado
                productosFiltrados = productosList.filter { producto ->
                    producto.nombre.contains(query, ignoreCase = true)
                }

                // Actualiza el adaptador del RecyclerView con la lista filtrada
                adapter.updateList(productosFiltrados)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    // Implementación de la interfaz ProductoClickListener
    override fun onProductoClick(producto: ProductoSQLiteModel) {
        // Aquí puedes agregar la lógica para manejar el clic en un producto
        // Por ejemplo, puedes mostrar un Toast con el nombre del producto
        Toast.makeText(this, "Producto clicado: ${producto.nombre}", Toast.LENGTH_SHORT).show()
    }
}
