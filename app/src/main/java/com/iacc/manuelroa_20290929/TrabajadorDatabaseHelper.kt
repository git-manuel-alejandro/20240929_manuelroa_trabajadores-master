package com.iacc.manuelroa_20290929

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.Cursor


class TrabajadorDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "trabajadores.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "trabajador"
        private const val COLUMN_ID = "id"
        private const val COLUMN_RUT = "rut"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_APELLIDOS = "apellidos"
        private const val COLUMN_ESTADO = "estado"
        private const val COLUMN_FECHA = "fecha"
        private const val COLUMN_HORA = "hora"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_RUT TEXT, " +
                "$COLUMN_NOMBRE TEXT, " +
                "$COLUMN_APELLIDOS TEXT, " +
                "$COLUMN_ESTADO TEXT, " +
                "$COLUMN_FECHA TEXT, " +
                "$COLUMN_HORA TEXT)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertarTrabajador(rut: String, nombre: String, apellidos: String, estado: String, fecha: String, hora: String): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_RUT, rut)
        values.put(COLUMN_NOMBRE, nombre)
        values.put(COLUMN_APELLIDOS, apellidos)
        values.put(COLUMN_ESTADO, estado)
        values.put(COLUMN_FECHA, fecha)
        values.put(COLUMN_HORA, hora)
        return db.insert(TABLE_NAME, null, values)
    }

    fun readAllTrabajadores(): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_ID DESC", null)
    }
}
