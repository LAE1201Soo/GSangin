package com.example.gsangin.model

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gsangin.ClienteSQLiteModel
import com.example.gsangin.ProductoAdapter
import com.example.gsangin.R

class Pedido1 : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductoAdapter
    private lateinit var productosList: List<ProductoSQLiteModel>
    private lateinit var productosFiltrados: List<ProductoSQLiteModel>
    private lateinit var clienteSeleccionado: ClienteSQLiteModel // Variable para almacenar la información del cliente

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedido1)

        // Obtén la información del cliente desde el intent
        clienteSeleccionado = intent.getSerializableExtra("clienteSeleccionado") as? ClienteSQLiteModel
            ?: throw IllegalArgumentException("Cliente no proporcionado en el intent")

        // Muestra la información del cliente en algún lugar de tu diseño, por ejemplo, un TextView

        val idclient: TextView = findViewById(R.id.idclienttxt)
        idclient.text = " ${clienteSeleccionado.id  }"
        val nombreClienteTextView: TextView = findViewById(R.id.nombreClienteTextView)
        nombreClienteTextView.text = " Cliente: ${clienteSeleccionado.nombre}"
        // ocultar el id para el usuario
        idclient.visibility = View.GONE



        // Inicializa el RecyclerView
        recyclerView = findViewById(R.id.recliclerProductos)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        // Accede a la base de datos y obtén la lista de productos
        val dbHelper = bdAdapter(this)
        productosList = dbHelper.getProductosList()

        // Inicializa la lista filtrada con la lista original al principio
        productosFiltrados = productosList

        // Inicializa y configura el adaptador con la lista de productos filtrados
        adapter = ProductoAdapter(productosFiltrados)
        recyclerView.adapter = adapter

        // Configura el EditText para la búsqueda de productos
        val editTextSearch = findViewById<EditText>(R.id.txtBuscar2)
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
}
