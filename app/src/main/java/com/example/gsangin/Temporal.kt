package com.example.gsangin

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gsangin.model.Pedido
import com.example.gsangin.model.PedidoAdapter
import com.example.gsangin.model.ProductoSQLiteModel
import com.example.gsangin.model.bdAdapter
import java.util.Locale

class Temporal : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var pedidoAdapter: PedidoAdapter
    private lateinit var dbHelper: bdAdapter
    private lateinit var btnEnviarCorreo:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temporal)

        dbHelper = bdAdapter(this)

        val btnEnviarCorreo: Button = findViewById(R.id.btnEnviar)

        recyclerView = findViewById(R.id.recyPedi)
        recyclerView.layoutManager = LinearLayoutManager(this)


        val listaPedidos: List<Pedido> = obtenerListaDePedidos()

        pedidoAdapter = PedidoAdapter(listaPedidos)
        recyclerView.adapter = pedidoAdapter

        btnEnviarCorreo.setOnClickListener {
            enviarCorreo()
        }
    }

    @SuppressLint("Range")
    private fun obtenerListaDePedidos(): List<Pedido> {

        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM ${bdAdapter.TABLE_PEDIDOS}", null)

        val listaPedidos = mutableListOf<Pedido>()

        while (cursor.moveToNext()) {
            val pedidoId = cursor.getInt(cursor.getColumnIndex(bdAdapter.COLUMN_PEDIDO_ID))
            val clienteId = cursor.getInt(cursor.getColumnIndex(bdAdapter.COLUMN_PEDIDO_CLIENTE_ID))
            val subtotal = cursor.getString(cursor.getColumnIndex(bdAdapter.COLUMN_PEDIDO_SUBTOTAL))
            val total = cursor.getString(cursor.getColumnIndex(bdAdapter.COLUMN_PEDIDO_TOTAL))
            val fecha = cursor.getString(cursor.getColumnIndex(bdAdapter.COLUMN_PEDIDO_FECHA))

            val productosConCantidad = obtenerProductosPorPedidoId(pedidoId)

            val pedido = Pedido(clienteId, fecha, subtotal, total, productosConCantidad)
            listaPedidos.add(pedido)
        }

        cursor.close()
        db.close()

        return listaPedidos
    }

    @SuppressLint("Range")
    private fun obtenerProductosPorPedidoId(pedidoId: Int): List<Pair<ProductoSQLiteModel, Int>> {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM ${bdAdapter.TABLE_PRODUCTOS_PEDIDO} WHERE ${bdAdapter.COLUMN_PRODUCTO_PEDIDO_PEDIDO_ID} = ?", arrayOf(pedidoId.toString()))

        val productosConCantidad = mutableListOf<Pair<ProductoSQLiteModel, Int>>()

        while (cursor.moveToNext()) {
            val productoId = cursor.getInt(cursor.getColumnIndex(bdAdapter.COLUMN_PRODUCTO_PEDIDO_PRODUCTO_ID))
            val cantidad = cursor.getInt(cursor.getColumnIndex(bdAdapter.COLUMN_PRODUCTO_PEDIDO_CANTIDAD))


            val producto = obtenerProductoPorId(productoId)

            productosConCantidad.add(producto to cantidad)
        }

        cursor.close()

        return productosConCantidad
    }

    @SuppressLint("Range")
    private fun obtenerProductoPorId(productoId: Int): ProductoSQLiteModel {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM ${bdAdapter.TABLE_PRODUCTOS} WHERE ${bdAdapter.COLUMN_PRODUCTO_ID} = ?", arrayOf(productoId.toString()))

        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndex(bdAdapter.COLUMN_PRODUCTO_ID))
        val clave = cursor.getString(cursor.getColumnIndex(bdAdapter.COLUMN_CLAVE))
        val nombre = cursor.getString(cursor.getColumnIndex(bdAdapter.COLUMN_PRODUCTO_NOMBRE))
        val descripcion = cursor.getString(cursor.getColumnIndex(bdAdapter.COLUMN_DESCRIPCION))
        val precio = cursor.getDouble(cursor.getColumnIndex(bdAdapter.COLUMN_PRECIO))
        val iva = cursor.getDouble(cursor.getColumnIndex(bdAdapter.COLUMN_IVA))
        val ieps = cursor.getDouble(cursor.getColumnIndex(bdAdapter.COLUMN_IEPS))

        cursor.close()


        val precioFormateado = String.format(Locale.US, "%.2f", precio)
        val ivaFormateado = String.format(Locale.US, "%.2f", iva)
        val iepsFormateado = String.format(Locale.US, "%.2f", ieps)

        return ProductoSQLiteModel(id, clave, nombre, descripcion, precioFormateado, ivaFormateado, iepsFormateado)
    }

    private fun enviarCorreo() {

        val listaPedidos: List<Pedido> = obtenerListaDePedidos()


        val contenidoCorreo = formatearContenidoCorreo(listaPedidos)


        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, "Datos del Pedido")
        intent.putExtra(Intent.EXTRA_TEXT, contenidoCorreo)


        startActivity(Intent.createChooser(intent, "Enviar correo electrónico"))
    }

    private fun formatearContenidoCorreo(listaPedidos: List<Pedido>): String {

        val stringBuilder = StringBuilder()

        for (pedido in listaPedidos) {
            stringBuilder.append("Cliente: ${pedido.clienteId}\n")
            stringBuilder.append("Fecha: ${pedido.fecha}\n")
            stringBuilder.append("Subtotal: ${pedido.subtotal}\n")
            stringBuilder.append("Total: ${pedido.total}\n\n")

            for ((producto, cantidad) in pedido.productos) {
                stringBuilder.append("Producto: ${producto.nombre}, Cantidad: $cantidad\n")
            }

            stringBuilder.append("\n-----------------\n\n")
        }

        return stringBuilder.toString()
    }

}
