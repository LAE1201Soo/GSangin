package com.example.gsangin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gsangin.model.ProductoSQLiteModel

class AnalizarPedido : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analizar_pedido)

        // Obtén la lista de productos seleccionados y sus cantidades del Intent
        val productosSeleccionados =
            intent.getSerializableExtra("productosSeleccionados") as? ArrayList<ProductoSQLiteModel>
                ?: throw IllegalArgumentException("Lista de productos no proporcionada en el intent")

        val cantidades =
            intent.getIntegerArrayListExtra("cantidades") ?: throw IllegalArgumentException("Lista de cantidades no proporcionada en el intent")
    }
}