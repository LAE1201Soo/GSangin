package com.example.gsangin.model

data class ProductoSQLiteModel(
    val id: Int,  //
    val clave: String,
    val nombre: String,
    val descripcion: String,
    val precio: String,// verificar que onda con estos
    val iva: String,
    val ieps: String
)
