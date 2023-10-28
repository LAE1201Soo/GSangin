package com.example.gsangin.model

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.gsangin.ClienteSQLiteModel

class bdAdapter  (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "aplicacionK.db"
        const val DATABASE_VERSION = 1

        // Define la tabla y las columnas que llevara la bd
        const val TABLE_CLIENTES = "clientes"
        const val COLUMN_ID = "id"
        const val COLUMN_NOMBRE = "nombre"
        const val COLUMN_RAZON_SOCIAL = "razon_social"
        const val COLUMN_CALLE ="calle"
        const val COLUMN_CP ="cp"
        const val COLUMN_CIUDAD ="ciudad"
        const val COLUMN_ESTADO ="estado"
        const val COLUMN_NUMERO ="numero"
        const val COLUMN_TELEFONO ="tel"


        // Crear la tabla
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
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_CLIENTES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CLIENTES")
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



}