package com.example.gsangin

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ClienteAdapter (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
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
}