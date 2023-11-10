package com.example.gsangin

import java.io.Serializable

data class ClienteSQLiteModel(
    val id: Int,
    val nombre: String,
    val razonSocial: String,
    val calle: String,
    val cp: String,
    val ciudad: String,
    val estado: String,
    val numero: String,
    val tel: String
): Serializable
