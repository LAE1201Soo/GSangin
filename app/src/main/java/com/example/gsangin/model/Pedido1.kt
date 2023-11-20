package com.example.gsangin.model

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gsangin.AnalizarPedido
import com.example.gsangin.ClienteSQLiteModel
import com.example.gsangin.ProductoAdapter
import com.example.gsangin.R

class Pedido1 : AppCompatActivity(), ProductoAdapter.ProductoClickListener, PrepedidoAdapter.ProductoClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductoAdapter
    private lateinit var productosList: List<ProductoSQLiteModel>
    private lateinit var productosFiltrados: List<ProductoSQLiteModel>
    private lateinit var clienteSeleccionado: ClienteSQLiteModel
    private lateinit var productosSeleccionados: MutableList<ProductoSQLiteModel>
    private lateinit var cantidades: MutableList<Int>
    private lateinit var adapterPrepedido: PrepedidoAdapter
    private lateinit var precioTotalTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedido1)

        // Inicializar otras variables y vistas
        productosSeleccionados = mutableListOf()
        cantidades = mutableListOf()
        precioTotalTextView = findViewById(R.id.totaltxt)

        // Inicializa el RecyclerView para mostrar los productos seleccionados
        val recyprepedido = findViewById<RecyclerView>(R.id.recyprepedido)
        val layoutManagerPrepedido = LinearLayoutManager(this)
        recyprepedido.layoutManager = layoutManagerPrepedido

        // Inicializa y configura el adaptador para mostrar los productos seleccionados
        adapterPrepedido = PrepedidoAdapter(productosSeleccionados, cantidades, this)
        recyprepedido.adapter = adapterPrepedido

        // Obtén la información del cliente desde el intent
        clienteSeleccionado = intent.getSerializableExtra("clienteSeleccionado") as? ClienteSQLiteModel
            ?: throw IllegalArgumentException("Cliente no proporcionado en el intent")

        // Muestra la información del cliente en algún lugar de tu diseño, por ejemplo, un TextView
        val idclient: TextView = findViewById(R.id.idclienttxt)
        idclient.text = " ${clienteSeleccionado.id}"
        val nombreClienteTextView: TextView = findViewById(R.id.nombreClienteTextView)
        nombreClienteTextView.text = " Cliente: ${clienteSeleccionado.nombre}"
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

        // Inicializa y configura el adaptador con la lista de productos filtrados y el listener
        adapter = ProductoAdapter(productosFiltrados, this)
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

    override fun onProductoClick(producto: ProductoSQLiteModel) {
        val index = productosSeleccionados.indexOf(producto)
        if (index == -1) {
            productosSeleccionados.add(producto)
            cantidades.add(1)
        } else {
            cantidades[index] += 1
        }

        try {
            val recyprepedido2 = findViewById<RecyclerView>(R.id.recyprepedido)
            val layoutManagerPrepedido = LinearLayoutManager(this)
            recyprepedido2.layoutManager = layoutManagerPrepedido

            val adapterPrepedido2 = PrepedidoAdapter(productosSeleccionados, cantidades, this)
            recyprepedido2.adapter = adapterPrepedido2

            // Actualiza el precio total después de cada clic en un producto
            actualizarPrecioTotal()

        } catch (e: Exception) {
            Log.e("error2", "Error al inicializar el nuevo adaptador: ${e.message}", e)
        }
    }

    private fun calcularPrecioTotal(): Double {
        var precioTotal = 0.0

        for (i in productosSeleccionados.indices) {
            val producto = productosSeleccionados[i]
            val cantidad = cantidades[i]

            // Intenta convertir el precio a Double antes de multiplicar
            val precioComoDouble = try {
                producto.precio.toDouble()
            } catch (e: NumberFormatException) {
                // Maneja la excepción si la conversión no es posible
                0.0
            }

            precioTotal += precioComoDouble * cantidad
        }

        return precioTotal
    }

    private fun actualizarPrecioTotal() {
        precioTotalTextView.text = "Subtotal: ${calcularPrecioTotal()}"
    }

    fun abrirOtraActividad(view: View) {
        val intent = Intent(this, AnalizarPedido::class.java)
        intent.putExtra("productosSeleccionados", ArrayList(productosSeleccionados))
        intent.putExtra("cantidades", ArrayList(cantidades))
        intent.putExtra("numeroCliente", clienteSeleccionado.id)
        intent.putExtra("nombreCliente", clienteSeleccionado.nombre)
        startActivity(intent)
    }
}


