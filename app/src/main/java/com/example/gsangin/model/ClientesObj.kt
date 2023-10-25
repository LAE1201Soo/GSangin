package com.example.gsangin.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ClientesObj {
    val Clientes = Retrofit.Builder()
        .baseUrl("http://ba1118c.online-server.cloud/kotlin/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = Clientes.create(ClientesServices::class.java)

}