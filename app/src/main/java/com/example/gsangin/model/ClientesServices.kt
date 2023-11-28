package com.example.gsangin.model
import android.util.Log
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ClientesServices {
    @GET("SWclientes.php")
    fun listClientesData(): Call<List<ClientesModel>>


}