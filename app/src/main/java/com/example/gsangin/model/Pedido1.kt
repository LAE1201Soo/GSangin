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
    private lateinit var clienteSeleccionado: ClienteSQLiteModel // Variable para almacenar la información del cliente
    private lateinit var productosSeleccionados: MutableList<ProductoSQLiteModel>
    private lateinit var cantidades: MutableList<Int>
    private lateinit var adapterPrepedido: PrepedidoAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedido1)

        //inicialilar la nueva lista :0
        productosSeleccionados = mutableListOf()
        //inicializar cantidades
        // En el onCreate de tu actividad
        cantidades = mutableListOf()


        // Inicializa el RecyclerView para mostrar los productos seleccionados
        val recyprepedido = findViewById<RecyclerView>(R.id.recyprepedido)
        val layoutManagerPrepedido = LinearLayoutManager(this)
        recyprepedido.layoutManager = layoutManagerPrepedido
        // Crear un objeto ProductoClickListener vacío para evitar problemas de valor nulo
        val emptyClickListener = object : ProductoAdapter.ProductoClickListener {
            override fun onProductoClick(producto: ProductoSQLiteModel) {
                // No necesitas hacer nada aquí, ya que esta lista no manejará clics
            }
        }
        // Inicializa y configura el adaptador para mostrar los productos seleccionados
        adapterPrepedido = PrepedidoAdapter(productosSeleccionados, cantidades, this)
        recyprepedido.adapter = adapterPrepedido
        // ver si inicio
        Log.d("Pedido1", "onCreate - Inicialización completada")


        // Obtén la información del cliente desde el intent
        clienteSeleccionado =
            intent.getSerializableExtra("clienteSeleccionado") as? ClienteSQLiteModel
                ?: throw IllegalArgumentException("Cliente no proporcionado en el intent")

        // Muestra la información del cliente en algún lugar de tu diseño, por ejemplo, un TextView
        val idclient: TextView = findViewById(R.id.idclienttxt)
        idclient.text = " ${clienteSeleccionado.id}"
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

    // Implementación de la interfaz ProductoClickListener
    override fun onProductoClick(producto: ProductoSQLiteModel) {
        Log.d("Pedido1", "onProductoClick - Producto seleccionado: ${producto.nombre}")

        // Verifica si el producto ya está en la lista
        val index = productosSeleccionados.indexOf(producto)
        if (index == -1) {
            // El producto no está en la lista, agrégalo
            productosSeleccionados.add(producto)
            cantidades.add(1) // Puedes ajustar esto según tus requisitos
            Log.d("Pedido1", "1")
        } else {
            // El producto ya está en la lista, actualiza la cantidad, por ejemplo, aumenta en 1
            cantidades[index] += 1
            Log.d("Pedido1", "2")
        }
        try {
            // Inicializa el nuevo RecyclerView

            val recyprepedido2 = findViewById<RecyclerView>(R.id.recyprepedido)

            val layoutManagerPrepedido = LinearLayoutManager(this)
            recyprepedido2.layoutManager = layoutManagerPrepedido


            // Crea un nuevo adaptador
            val adapterPrepedido2 = PrepedidoAdapter(productosSeleccionados, cantidades, this)

            // Asigna el nuevo adaptador
            recyprepedido2.adapter = adapterPrepedido2
            Log.d("entro", "logre asignar valores")
            // Imprime un mensaje indicando que la inicialización fue exitosa
            Log.d("Pedido1", "Nuevo adaptador inicializado con éxito")

        } catch (e: Exception) {
            // Maneja la excepción aquí
            Log.e("error2", "Error al inicializar el nuevo adaptador: ${e.message}", e)

        }
    }

    fun abrirOtraActividad(view: View) {

        val intent = Intent(this, AnalizarPedido::class.java)

        // Agrega logs para verificar la lista antes de pasarla al Intent
        Log.d("Pedido1", "Productos Seleccionados antes de pasar a la siguiente actividad:")
        for (producto in productosSeleccionados) {
            Log.d("Pedido1", "ID: ${producto.id}, Nombre: ${producto.nombre}")
        }

        // Pasa la lista de productos seleccionados y sus cantidades como extras en el Intent
        intent.putExtra("productosSeleccionados", ArrayList(productosSeleccionados))
        intent.putExtra("cantidades", ArrayList(cantidades))

        // Pasa la información del cliente como extras en el Intent
        intent.putExtra("numeroCliente", clienteSeleccionado.id)
        intent.putExtra("nombreCliente", clienteSeleccionado.nombre)



        startActivity(intent)
    }

}

