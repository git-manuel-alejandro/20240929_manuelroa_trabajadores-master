package com.iacc.manuelroa_20290929

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView



data class Trabajador(val rut: String, val nombre: String, val apellidos: String, val estado: String, val fecha: String, val hora: String)
class TrabajadorAdapter(
    private val trabajadores: List<Trabajador>,
    private val context: Context
) : RecyclerView.Adapter<TrabajadorAdapter.TrabajadorViewHolder>() {

    class TrabajadorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvRut: TextView = itemView.findViewById(R.id.tvRut)
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)
        val tvApellidos: TextView = itemView.findViewById(R.id.tvApellidos)
        val tvEstado: TextView = itemView.findViewById(R.id.tvEstado)
        val tvFechaHora: TextView = itemView.findViewById(R.id.tvFechaHora)
        val btnShare: Button = itemView.findViewById(R.id.btnShare)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrabajadorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trabajador_item, parent, false)
        return TrabajadorViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrabajadorViewHolder, position: Int) {
        val trabajador = trabajadores[position]
        holder.tvRut.text = trabajador.rut
        holder.tvNombre.text = trabajador.nombre
        holder.tvApellidos.text = trabajador.apellidos
        holder.tvEstado.text = trabajador.estado
        holder.tvFechaHora.text = "${trabajador.fecha} ${trabajador.hora}"


        holder.btnShare.setOnClickListener {
            compartirRegistro(trabajador)
        }
    }

    override fun getItemCount(): Int {
        return trabajadores.size
    }

    private fun compartirRegistro(trabajador: Trabajador) {
        val shareText = """
            RUT: ${trabajador.rut}
            Nombre: ${trabajador.nombre}
            Apellidos: ${trabajador.apellidos}
            Estado: ${trabajador.estado}
            Fecha: ${trabajador.fecha}
            Hora: ${trabajador.hora}
        """.trimIndent()

        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }
        context.startActivity(Intent.createChooser(intent, "Compartir registro"))
    }
}
