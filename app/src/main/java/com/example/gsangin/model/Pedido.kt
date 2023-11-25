package com.example.gsangin.model

data class Pedido(
    val clienteId: Int,
    val clienteNombre: String?,
    val subtotal: String,
    val total: String,
    val productosConCantidad: List<Pair<ProductoSQLiteModel, Int>>
)