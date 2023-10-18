package com.example.gsangin.model
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ClientesServices {
    @GET("")
    fun listClientesData(): Call<ClientesModel>


}