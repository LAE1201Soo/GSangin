package com.example.gsangin.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ProductosObj {
    val Productos = Retrofit.Builder()
        .baseUrl("http://ba1118c.online-server.cloud/kotlin/productos/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = Productos.create(ProductosServices::class.java)
}