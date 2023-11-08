package com.example.gsangin.model

data class ProductoSQLiteModel(
    val id: Int,  //
    val clave: String,
    val nombre: String,
    val descripcion: String,
    val precio: Double,// verificar que onda con estos
    val iva: Double,
    val ieps: Double
)
