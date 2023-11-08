package com.example.gsangin

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gsangin.model.ClientesObj
import com.example.gsangin.model.ProductosObj
import com.example.gsangin.model.bdAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class Sincronizar : AppCompatActivity() {
    private lateinit var btnSincronizar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sincronizar)

        btnSincronizar = findViewById(R.id.btnSincronizarD)




        btnSincronizar.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    // Sincronizar clientes
                    val clientesResponse = ClientesObj.service.listClientesData().execute()

                    if (clientesResponse.isSuccessful) {
                        val bodyClientes = clientesResponse.body()
                        if (bodyClientes != null) {
                            val dbHelper = bdAdapter(this@Sincronizar)
                            val db = dbHelper.writableDatabase

                            // Borra los datos existentes en la tabla de clientes antes de guardar los nuevos datos
                            db.execSQL("DELETE FROM ${bdAdapter.TABLE_CLIENTES}")

                            for (cliente in bodyClientes) {
                                val values = ContentValues().apply {
                                    put(bdAdapter.COLUMN_ID, cliente.id)
                                    put(bdAdapter.COLUMN_NOMBRE, cliente.nombre)
                                    put(bdAdapter.COLUMN_RAZON_SOCIAL, cliente.razon_social)
                                    put(bdAdapter.COLUMN_CALLE, cliente.calle)
                                    put(bdAdapter.COLUMN_CP, cliente.cp)
                                    put(bdAdapter.COLUMN_CIUDAD, cliente.ciudad)
                                    put(bdAdapter.COLUMN_ESTADO, cliente.estado)
                                    put(bdAdapter.COLUMN_NUMERO, cliente.numero)
                                    put(bdAdapter.COLUMN_TELEFONO, cliente.tel)
                                }

                                // Inserta el registro en la tabla de clientes
                                val rowId = db.insert(bdAdapter.TABLE_CLIENTES, null, values)
                                if (rowId.toInt() != -1) {
                                    Log.d("Sincronizar", "Cliente insertado: ${cliente.nombre}")
                                } else {
                                    Log.e("Sincronizar", "Error al insertar cliente: ${cliente.nombre}")
                                }
                            }

                            // Sincronizar productos
                            val productosResponse = ProductosObj.service.listProductosData().execute()

                            if (productosResponse.isSuccessful) {
                                val bodyProductos = productosResponse.body()
                                if (bodyProductos != null) {
                                    // Borra los datos existentes en la tabla de productos antes de guardar los nuevos datos
                                    db.execSQL("DELETE FROM ${bdAdapter.TABLE_PRODUCTOS}")

                                    for (producto in bodyProductos) {
                                        val values = ContentValues().apply {
                                            put(bdAdapter.COLUMN_PRODUCTO_ID, producto.id)
                                            put(bdAdapter.COLUMN_CLAVE, producto.clave)
                                            put(bdAdapter.COLUMN_PRODUCTO_NOMBRE, producto.nombre)
                                            put(bdAdapter.COLUMN_DESCRIPCION, producto.descripcion)
                                            put(bdAdapter.COLUMN_PRECIO, producto.precio)
                                            put(bdAdapter.COLUMN_IVA, producto.iva)
                                            put(bdAdapter.COLUMN_IEPS, producto.ieps)
                                        }

                                        // Inserta el registro en la tabla de productos
                                        val rowId = db.insert(bdAdapter.TABLE_PRODUCTOS, null, values)
                                        if (rowId.toInt() != -1) {
                                            Log.d("SincronizarProducto", "Producto insertado: ${producto.nombre}")
                                        } else {
                                            Log.e("SincronizarProducto", "Error al insertar producto: ${producto.nombre}")
                                        }
                                    }

                                    // Cierra la base de datos
                                    db.close()

                                    // Muestra un mensaje de éxito en el hilo de la interfaz de usuario
                                    runOnUiThread {
                                        Toast.makeText(
                                            this@Sincronizar,
                                            "Conexión exitosa",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            } else {
                                Log.e(
                                    "Sincronizar",
                                    "Error en la solicitud HTTP de productos: ${productosResponse.code()} ${productosResponse.message()}"
                                )
                            }
                        }
                    } else {
                        Log.e(
                            "Sincronizar",
                            "Error en la solicitud HTTP de clientes: ${clientesResponse.code()} ${clientesResponse.message()}"
                        )
                    }
                } catch (e: Exception) {
                    Log.e("Sincronizar", "Error al sincronizar: ${e.message}", e)
                    runOnUiThread {
                        Toast.makeText(
                            this@Sincronizar,
                            "Error al sincronizar: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }


    }
}