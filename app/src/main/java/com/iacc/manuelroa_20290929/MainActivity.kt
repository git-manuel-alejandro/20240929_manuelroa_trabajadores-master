package com.iacc.manuelroa_20290929

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var databaseHelper: TrabajadorDatabaseHelper
    private lateinit var trabajadorAdapter: TrabajadorAdapter
    private val trabajadoresList = mutableListOf<Trabajador>()

    private lateinit var editTextRut: EditText
    private lateinit var editTextNombre: EditText
    private lateinit var editTextApellidos: EditText
    private lateinit var radioGroupEstado: RadioGroup
    private lateinit var buttonAgregar: Button
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseHelper = TrabajadorDatabaseHelper(this)


        editTextRut = findViewById(R.id.etRut)
        editTextNombre = findViewById(R.id.etNombre)
        editTextApellidos = findViewById(R.id.etApellidos)
        radioGroupEstado = findViewById(R.id.radioGroupEstado)
        buttonAgregar = findViewById(R.id.btnRegistrar)
        recyclerView = findViewById(R.id.recyclerViewTrabajadores)


        recyclerView.layoutManager = LinearLayoutManager(this)
        trabajadorAdapter = TrabajadorAdapter(trabajadoresList, this)
        recyclerView.adapter = trabajadorAdapter


        cargarTrabajadores()


        buttonAgregar.setOnClickListener {
            agregarTrabajador()
        }
    }

    private fun agregarTrabajador() {
        val rut = editTextRut.text.toString().trim()
        val nombre = editTextNombre.text.toString().trim()
        val apellidos = editTextApellidos.text.toString().trim()


        val estadoId = radioGroupEstado.checkedRadioButtonId
        val estado = if (estadoId != -1) {
            findViewById<RadioButton>(estadoId).text.toString()
        } else {
            Toast.makeText(this, "Seleccione un estado", Toast.LENGTH_SHORT).show()
            return
        }


        val currentTimeMillis = System.currentTimeMillis()


        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val fecha = dateFormat.format(Date(currentTimeMillis))


        val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val hora = timeFormat.format(Date(currentTimeMillis))


        val id = databaseHelper.insertarTrabajador(rut, nombre, apellidos, estado, fecha, hora)

        if (id != -1L) {
            Toast.makeText(this, "Trabajador agregado", Toast.LENGTH_SHORT).show()
            cargarTrabajadores()
            clearFields()
        } else {
            Toast.makeText(this, "Error al agregar trabajador", Toast.LENGTH_SHORT).show()
        }
    }

    private fun cargarTrabajadores() {

        trabajadoresList.clear()

        val cursor = databaseHelper.readAllTrabajadores()
        if (cursor.moveToFirst()) {
            do {
                val rut = cursor.getString(cursor.getColumnIndex("rut"))
                val nombre = cursor.getString(cursor.getColumnIndex("nombre"))
                val apellidos = cursor.getString(cursor.getColumnIndex("apellidos"))
                val estado = cursor.getString(cursor.getColumnIndex("estado"))
                val fecha = cursor.getString(cursor.getColumnIndex("fecha"))
                val hora = cursor.getString(cursor.getColumnIndex("hora"))


                val trabajador = Trabajador(rut, nombre, apellidos, estado, fecha, hora)
                trabajadoresList.add(trabajador)
            } while (cursor.moveToNext())
        }


        trabajadorAdapter.notifyDataSetChanged()


        cursor.close()
    }

    private fun clearFields() {
        editTextRut.text.clear()
        editTextNombre.text.clear()
        editTextApellidos.text.clear()
        radioGroupEstado.clearCheck()
    }
}
