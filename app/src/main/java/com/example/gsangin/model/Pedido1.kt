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
import java.text.DecimalFormat

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
    private lateinit var subtotalTextView: TextView
    private lateinit var totalTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedido1)

        productosSeleccionados = mutableListOf()
        cantidades = mutableListOf()

        subtotalTextView = findViewById(R.id.Subtxt)
        totalTextView = findViewById(R.id.txtTTF)

        val recyprepedido = findViewById<RecyclerView>(R.id.recyprepedido)
        val layoutManagerPrepedido = LinearLayoutManager(this)
        recyprepedido.layoutManager = layoutManagerPrepedido
        adapterPrepedido = PrepedidoAdapter(productosSeleccionados, cantidades, this)
        recyprepedido.adapter = adapterPrepedido

        clienteSeleccionado = intent.getSerializableExtra("clienteSeleccionado") as? ClienteSQLiteModel
            ?: throw IllegalArgumentException("Cliente no proporcionado en el intent")

        val idclient: TextView = findViewById(R.id.idclienttxt)
        idclient.text = " ${clienteSeleccionado.id}"
        val nombreClienteTextView: TextView = findViewById(R.id.nombreClienteTextView)
        nombreClienteTextView.text = " Cliente: ${clienteSeleccionado.nombre}"
        idclient.visibility = View.GONE

        recyclerView = findViewById(R.id.recliclerProductos)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val dbHelper = bdAdapter(this)
        productosList = dbHelper.getProductosList()
        productosFiltrados = productosList
        adapter = ProductoAdapter(productosFiltrados, this)
        recyclerView.adapter = adapter

        val editTextSearch = findViewById<EditText>(R.id.txtBuscar2)
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

        actualizarPrecios()
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

            actualizarPrecios()

        } catch (e: Exception) {
            Log.e("error2", "Error al inicializar el nuevo adaptador: ${e.message}", e)
        }
    }

    private fun calcularPrecioSubtotal(): Double {
        var subtotal = 0.0

        for (i in productosSeleccionados.indices) {
            val producto = productosSeleccionados[i]
            val cantidad = cantidades[i]

            val precioComoDouble = try {
                producto.precio.toDouble()
            } catch (e: NumberFormatException) {
                0.0
            }

            subtotal += precioComoDouble * cantidad
        }

        return subtotal
    }

    private fun calcularPrecioFinal(subtotal: Double): Double {
        var total = subtotal

        for (i in productosSeleccionados.indices) {
            val producto = productosSeleccionados[i]
            val cantidad = cantidades[i]

            val precioComoDouble = try {
                producto.precio.toDouble()
            } catch (e: NumberFormatException) {
                0.0
            }

            val iepsComoPorcentaje = try {
                producto.ieps.toDouble() / 100.0
            } catch (e: NumberFormatException) {
                0.0
            }

            val ivaComoPorcentaje = try {
                producto.iva.toDouble() / 100.0
            } catch (e: NumberFormatException) {
                0.0
            }

            total += precioComoDouble * iepsComoPorcentaje * cantidad
            total += precioComoDouble * ivaComoPorcentaje * cantidad
        }

        return total
    }

    private fun actualizarPrecios() {
        val subtotal = calcularPrecioSubtotal()
        val total = calcularPrecioFinal(subtotal)

        val subtotalRedondeado = redondearADosDecimales(subtotal)
        val totalRedondeado = redondearADosDecimales(total)

        subtotalTextView.text = "Subtotal: $subtotalRedondeado"
        totalTextView.text = "Total: $totalRedondeado"
    }

    fun abrirOtraActividad(view: View) {
        if (productosSeleccionados.isNotEmpty()) {
            val intent = Intent(this, AnalizarPedido::class.java)
            intent.putExtra("productosSeleccionados", ArrayList(productosSeleccionados))
            intent.putExtra("cantidades", ArrayList(cantidades))
            intent.putExtra("numeroCliente", clienteSeleccionado.id)
            intent.putExtra("nombreCliente", clienteSeleccionado.nombre)

            val subtotal = calcularPrecioSubtotal()
            val total = calcularPrecioFinal(subtotal)

            val subtotalRedondeado = redondearADosDecimales(subtotal)
            val totalRedondeado = redondearADosDecimales(total)

            intent.putExtra("subtotalRedondeado", subtotalRedondeado)
            intent.putExtra("totalRedondeado", totalRedondeado)
            startActivity(intent)
        } else {

            Toast.makeText(this, "La lista de productos está vacía", Toast.LENGTH_SHORT).show()
        }
    }

    private fun redondearADosDecimales(valor: Double): String {
        val df = DecimalFormat("#.##")
        return df.format(valor)
    }
    fun restablecerPrepedido(view: View) {
        productosSeleccionados.clear()
        cantidades.clear()

        try {
            val recyprepedido = findViewById<RecyclerView>(R.id.recyprepedido)
            val layoutManagerPrepedido = LinearLayoutManager(this)
            recyprepedido.layoutManager = layoutManagerPrepedido
            adapterPrepedido = PrepedidoAdapter(productosSeleccionados, cantidades, this)
            recyprepedido.adapter = adapterPrepedido

            actualizarPrecios()
        } catch (e: Exception) {
            Log.e("errorRestablecer", "Error al restablecer prepedido: ${e.message}", e)
        }
    }
}


