package com.example.gsangin.model

import retrofit2.Call
import retrofit2.http.GET

interface ProductosServices {
    @GET("SWproductos.php")
    fun listProductosData(): Call<List<ProductoModel>> // Cambio en el tipo de retorno
}