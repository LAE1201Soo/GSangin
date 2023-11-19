package com.example.gsangin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gsangin.model.ProductoSQLiteModel
import com.example.gsangin.model.ProductosSeleccionadosAdapter

class AnalizarPedido : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analizar_pedido)

        // Obtén la lista de productos seleccionados y sus cantidades del Intent
        val productosSeleccionados =
            intent.getSerializableExtra("productosSeleccionados") as? ArrayList<ProductoSQLiteModel>
                ?: throw IllegalArgumentException("Lista de productos no proporcionada en el intent")

        val cantidades =
            intent.getIntegerArrayListExtra("cantidades")
                ?: throw IllegalArgumentException("Lista de cantidades no proporcionada en el intent")

        // Combina los productos seleccionados con sus cantidades
        val productosConCantidad = productosSeleccionados.zip(cantidades)

        // Configura el RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyComfirma)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Crea y asigna el adaptador
        val adapter = ProductosSeleccionadosAdapter(productosConCantidad)
        recyclerView.adapter = adapter
    }
}