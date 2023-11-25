package com.example.gsangin

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gsangin.model.ProductoSQLiteModel
import com.example.gsangin.model.ProductosSeleccionadosAdapter
import com.example.gsangin.model.bdAdapter

import android.util.Log

class AnalizarPedido : AppCompatActivity() {

    private lateinit var btnComfirmar: Button
    private lateinit var dbHelper: bdAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analizar_pedido)

        dbHelper = bdAdapter(this)

        btnComfirmar = findViewById(R.id.btComfirmsrPP)

        val numeroCliente = intent.getIntExtra("numeroCliente", 0)
        val nombreCliente = intent.getStringExtra("nombreCliente")

        val numeroClienteTextView: TextView = findViewById(R.id.txtidC)
        val nombreClienteTextView: TextView = findViewById(R.id.txtN)

        numeroClienteTextView.text = "Número de Cliente: $numeroCliente"
        nombreClienteTextView.text = "Nombre de Cliente: $nombreCliente"

        val productosSeleccionados =
            intent.getSerializableExtra("productosSeleccionados") as? ArrayList<ProductoSQLiteModel>
                ?: throw IllegalArgumentException("Lista de productos no proporcionada en el intent")

        val cantidades =
            intent.getIntegerArrayListExtra("cantidades")
                ?: throw IllegalArgumentException("Lista de cantidades no proporcionada en el intent")

        val productosConCantidad = productosSeleccionados.zip(cantidades)

        val recyclerView = findViewById<RecyclerView>(R.id.recyComfirma)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = ProductosSeleccionadosAdapter(productosConCantidad)
        recyclerView.adapter = adapter

        val subtotalRedondeado = intent.getStringExtra("subtotalRedondeado") ?: "0.00"
        val totalRedondeado = intent.getStringExtra("totalRedondeado") ?: "0.00"

        val subtotalTextView: TextView = findViewById(R.id.txtsub)
        val totalTextView: TextView = findViewById(R.id.txttotal)

        subtotalTextView.text = "Subtotal: $subtotalRedondeado"
        totalTextView.text = "Total con ipes e iva : $totalRedondeado"

        btnComfirmar.setOnClickListener {
            guardarPedidoEnBaseDeDatos(numeroCliente, nombreCliente, productosConCantidad, subtotalRedondeado, totalRedondeado)
            // Puedes agregar más lógica aquí si es necesario
        }
    }

    private fun guardarPedidoEnBaseDeDatos(
        clienteId: Int,
        clienteNombre: String?,
        productosConCantidad: List<Pair<ProductoSQLiteModel, Int>>,
        subtotal: String,
        total: String
    ) {
        val db = dbHelper.writableDatabase

        // Insertar pedido
        val pedidoValues = ContentValues().apply {
            put(bdAdapter.COLUMN_PEDIDO_CLIENTE_ID, clienteId)
            put(bdAdapter.COLUMN_PEDIDO_SUBTOTAL, subtotal)
            put(bdAdapter.COLUMN_PEDIDO_TOTAL, total)
        }
        val pedidoId = db.insert(bdAdapter.TABLE_PEDIDOS, null, pedidoValues)

        Log.d("AnalizarPedido", "Pedido insertado con ID: $pedidoId, Cliente ID: $clienteId, Cliente Nombre: $clienteNombre, Subtotal: $subtotal, Total: $total")

        // Insertar cada producto en ProductoPedido
        for ((producto, cantidad) in productosConCantidad) {
            val productoPedidoValues = ContentValues().apply {
                put(bdAdapter.COLUMN_PRODUCTO_PEDIDO_PEDIDO_ID, pedidoId)
                put(bdAdapter.COLUMN_PRODUCTO_PEDIDO_PRODUCTO_ID, producto.id)
                put(bdAdapter.COLUMN_PRODUCTO_PEDIDO_CANTIDAD, cantidad)
            }
            val productoPedidoId = db.insert(bdAdapter.TABLE_PRODUCTOS_PEDIDO, null, productoPedidoValues)

            Log.d("AnalizarPedido", "Producto del pedido insertado con ID: $productoPedidoId, Producto ID: ${producto.id}, Cantidad: $cantidad")
        }

        db.close()

        Toast.makeText(this, "Pedido realizado", Toast.LENGTH_SHORT).show()


        val intent = Intent(this, MenuPrincipal::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}

