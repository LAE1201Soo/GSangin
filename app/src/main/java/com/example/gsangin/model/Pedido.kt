package com.example.gsangin.model

data class Pedido(
    val clienteId: Int,
    val fecha: String,
    val subtotal: String,
    val total: String,
    val productos: List<Pair<ProductoSQLiteModel, Int>> )


