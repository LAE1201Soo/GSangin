package com.example.gsangin

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

class AnalizarPedido : AppCompatActivity() {
    private  lateinit var  btnComfirmar: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analizar_pedido)

        // iniciar el boton de comfirmar
        btnComfirmar= findViewById(R.id.btComfirmsrPP)


        // Obtén la información del cliente del Intent
        val numeroCliente = intent.getIntExtra("numeroCliente", 0)
        val nombreCliente = intent.getStringExtra("nombreCliente")

        val numeroClienteTextView: TextView = findViewById(R.id.txtidC)
        val nombreClienteTextView: TextView = findViewById(R.id.txtN)

        // Muestra la información del cliente en los TextViews
        numeroClienteTextView.text = "Número de Cliente: $numeroCliente"
        nombreClienteTextView.text = "Nombre de Cliente: $nombreCliente"

        // Obtén la lista de productos seleccionados y sus cantidades del Intent
        val productosSeleccionados =
            intent.getSerializableExtra("productosSeleccionados") as? ArrayList<ProductoSQLiteModel>
                ?: throw IllegalArgumentException("Lista de productos no proporcionada en el intent")

        val cantidades =
            intent.getIntegerArrayListExtra("cantidades")
                ?: throw IllegalArgumentException("Lista de cantidades no proporcionada en el intent")


        val productosConCantidad = productosSeleccionados.zip(cantidades)


        val recyclerView = findViewById<RecyclerView>(R.id.recyComfirma)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Crea y asigna el adaptador
        val adapter = ProductosSeleccionadosAdapter(productosConCantidad)
        recyclerView.adapter = adapter

        //  subtotal y el total redondeado del Intent
        val subtotalRedondeado = intent.getStringExtra("subtotalRedondeado")  ?: "0.00"
        val totalRedondeado = intent.getStringExtra("totalRedondeado") ?: "0.00"

// meterlos al textview
        val subtotalTextView: TextView = findViewById(R.id.txtsub)
        val totalTextView: TextView = findViewById(R.id.txttotal)

// Muestra el subtotal y el total en los TextViews
        subtotalTextView.text = "Subtotal: $subtotalRedondeado"
        totalTextView.text = "Total con ipes e iva : $totalRedondeado"

        // btn congfirmare
        btnComfirmar.setOnClickListener {
            val mensaje = "Funcion no disponible " +
                    "entretente gustavo  "
            val tostada = Toast.makeText(this, mensaje, Toast.LENGTH_SHORT)
            tostada.show()
            // Redirigir a tu enlace de TikTok
            val enlaceTikTok = "https://vm.tiktok.com/ZMjEMqMSR/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(enlaceTikTok))
            startActivity(intent)
        }
    }

}