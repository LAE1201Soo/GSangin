package com.example.gsangin.model

data class ProductoModel(
    val id: Int,  //
    val clave: String,
    val nombre: String,
    val descripcion: String,
    val precio: Float,
    val iva: Float,
    val ieps: Float
)
