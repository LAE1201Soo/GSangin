package com.example.gsangin.model

data class Pedido(
    val clienteId: Int,
    val fecha: String?,
    val subtotal: String,
    val total: String,
    val productosConCantidad: List<Pair<ProductoSQLiteModel, Int>>
)

