package com.example.gsangin.model

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.gsangin.ClienteSQLiteModel
import java.util.Locale

class bdAdapter(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "aplicacionK.db"
        const val DATABASE_VERSION = 3


        // Nueva tabla para pedidos
        const val TABLE_PEDIDOS = "pedidos"
        const val COLUMN_PEDIDO_ID = "id"
        const val COLUMN_PEDIDO_CLIENTE_ID = "cliente_id"
        const val COLUMN_PEDIDO_SUBTOTAL = "subtotal"
        const val COLUMN_PEDIDO_TOTAL = "total"

        // Nueva tabla para productos del pedido
        const val TABLE_PRODUCTOS_PEDIDO = "productos_pedido"
        const val COLUMN_PRODUCTO_PEDIDO_ID = "id"
        const val COLUMN_PRODUCTO_PEDIDO_PEDIDO_ID = "pedido_id"
        const val COLUMN_PRODUCTO_PEDIDO_PRODUCTO_ID = "producto_id"
        const val COLUMN_PRODUCTO_PEDIDO_CANTIDAD = "cantidad"

        // Define la tabla y las columnas que llevará la base de datos para clientes
        const val TABLE_CLIENTES = "clientes"
        const val COLUMN_ID = "id"
        const val COLUMN_NOMBRE = "nombre"
        const val COLUMN_RAZON_SOCIAL = "razon_social"
        const val COLUMN_CALLE = "calle"
        const val COLUMN_CP = "cp"
        const val COLUMN_CIUDAD = "ciudad"
        const val COLUMN_ESTADO = "estado"
        const val COLUMN_NUMERO = "numero"
        const val COLUMN_TELEFONO = "tel"

        // Define la tabla y las columnas que llevará la base de datos para productos
        const val TABLE_PRODUCTOS = "productos"
        const val COLUMN_PRODUCTO_ID = "id"  //
        const val COLUMN_CLAVE = "clave"
        const val COLUMN_PRODUCTO_NOMBRE = "nombre"  //
        const val COLUMN_DESCRIPCION = "descripcion"
        const val COLUMN_PRECIO = "precio"
        const val COLUMN_IVA = "iva"
        const val COLUMN_IEPS = "ieps"

        // Crear la tabla para clientes
        private const val CREATE_TABLE_CLIENTES = "CREATE TABLE $TABLE_CLIENTES (" +
                "$COLUMN_ID INTEGER PRIMARY KEY," +
                "$COLUMN_NOMBRE TEXT," +
                "$COLUMN_RAZON_SOCIAL TEXT," +
                "$COLUMN_CALLE TEXT," +
                "$COLUMN_CP TEXT," +
                "$COLUMN_CIUDAD TEXT," +
                "$COLUMN_ESTADO TEXT," +
                "$COLUMN_NUMERO TEXT," +
                "$COLUMN_TELEFONO TEXT" +
                ")"

        // Crear la tabla para productos
        private const val CREATE_TABLE_PRODUCTOS = "CREATE TABLE $TABLE_PRODUCTOS (" +
                "$COLUMN_PRODUCTO_ID INTEGER PRIMARY KEY," +
                "$COLUMN_CLAVE TEXT," +
                "$COLUMN_PRODUCTO_NOMBRE TEXT," +
                "$COLUMN_DESCRIPCION TEXT," +
                "$COLUMN_PRECIO REAL," +
                "$COLUMN_IVA REAL," +
                "$COLUMN_IEPS REAL" +
                ")"
        // tabla pedidos
        private const val CREATE_TABLE_PEDIDOS = "CREATE TABLE $TABLE_PEDIDOS (" +
                "$COLUMN_PEDIDO_ID INTEGER PRIMARY KEY," +
                "$COLUMN_PEDIDO_CLIENTE_ID INTEGER," +
                "$COLUMN_PEDIDO_SUBTOTAL TEXT," +
                "$COLUMN_PEDIDO_TOTAL TEXT" +
                ")"
        // productos pedidos
        private const val CREATE_TABLE_PRODUCTOS_PEDIDO = "CREATE TABLE $TABLE_PRODUCTOS_PEDIDO (" +
                "$COLUMN_PRODUCTO_PEDIDO_ID INTEGER PRIMARY KEY," +
                "$COLUMN_PRODUCTO_PEDIDO_PEDIDO_ID INTEGER," +
                "$COLUMN_PRODUCTO_PEDIDO_PRODUCTO_ID INTEGER," +
                "$COLUMN_PRODUCTO_PEDIDO_CANTIDAD INTEGER," +
                "FOREIGN KEY($COLUMN_PRODUCTO_PEDIDO_PEDIDO_ID) REFERENCES $TABLE_PEDIDOS($COLUMN_PEDIDO_ID)," +
                "FOREIGN KEY($COLUMN_PRODUCTO_PEDIDO_PRODUCTO_ID) REFERENCES $TABLE_PRODUCTOS($COLUMN_PRODUCTO_ID)" +
                ")"


    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_CLIENTES)
        db.execSQL(CREATE_TABLE_PRODUCTOS)
        db.execSQL(CREATE_TABLE_PEDIDOS)
        db.execSQL(CREATE_TABLE_PRODUCTOS_PEDIDO)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CLIENTES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PRODUCTOS")
        onCreate(db)
    }
    // Función para insertar un cliente en la base de datos
    fun insertCliente(cliente: ClienteSQLiteModel): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NOMBRE, cliente.nombre)
        values.put(COLUMN_RAZON_SOCIAL, cliente.razonSocial)
        values.put(COLUMN_CALLE, cliente.calle)
        values.put(COLUMN_CP, cliente.cp)
        values.put(COLUMN_CIUDAD, cliente.ciudad)
        values.put(COLUMN_ESTADO, cliente.estado)
        values.put(COLUMN_NUMERO, cliente.numero)
        values.put(COLUMN_TELEFONO, cliente.tel)

        val id = db.insert(TABLE_CLIENTES, null, values)
        db.close()
        return id
    }


    // Función para obtener todos los clientes de la base de datos

    @SuppressLint("Range")
    fun getClientesList(): List<ClienteSQLiteModel> {
        val clientesList = mutableListOf<ClienteSQLiteModel>()
        val db = this.readableDatabase

        val cursor = db.query(
            TABLE_CLIENTES,
            null,  // Todas las columnas
            null,
            null,
            null,
            null,
            null
        )

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
            val nombre = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE))
            val razonSocial = cursor.getString(cursor.getColumnIndex(COLUMN_RAZON_SOCIAL))
            val calle = cursor.getString(cursor.getColumnIndex(COLUMN_CALLE))
            val cp = cursor.getString(cursor.getColumnIndex(COLUMN_CP))
            val ciudad = cursor.getString(cursor.getColumnIndex(COLUMN_CIUDAD))
            val estado = cursor.getString(cursor.getColumnIndex(COLUMN_ESTADO))
            val numero = cursor.getString(cursor.getColumnIndex(COLUMN_NUMERO))
            val tel = cursor.getString(cursor.getColumnIndex(COLUMN_TELEFONO))

            val cliente = ClienteSQLiteModel(id, nombre, razonSocial, calle, cp, ciudad, estado, numero, tel)
            clientesList.add(cliente)
        }

        cursor.close()
        db.close()

        return clientesList
    }
    // Función para obtener todos los productos de la base de datos
    @SuppressLint("Range")
    fun getProductosList(): List<ProductoSQLiteModel> {
        val productosList = mutableListOf<ProductoSQLiteModel>()
        val db = this.readableDatabase

        val cursor = db.query(
            TABLE_PRODUCTOS,
            null,  // Todas las columnas
            null,
            null,
            null,
            null,
            null
        )

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex(COLUMN_PRODUCTO_ID))
            val clave = cursor.getString(cursor.getColumnIndex(COLUMN_CLAVE))
            val nombre = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCTO_NOMBRE))
            val descripcion = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPCION))
            val precio = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRECIO))
            val iva = cursor.getDouble(cursor.getColumnIndex(COLUMN_IVA))
            val ieps = cursor.getDouble(cursor.getColumnIndex(COLUMN_IEPS))

            // Formatea los valores con dos decimales
            val precioFormateado = String.format(Locale.US, "%.2f", precio)
            val ivaFormateado = String.format(Locale.US, "%.2f", iva)
            val iepsFormateado = String.format(Locale.US, "%.2f", ieps)

            val producto = ProductoSQLiteModel(id, clave, nombre, descripcion, precioFormateado, ivaFormateado, iepsFormateado)
            productosList.add(producto)
        }

        cursor.close()
        db.close()

        return productosList
    }


}