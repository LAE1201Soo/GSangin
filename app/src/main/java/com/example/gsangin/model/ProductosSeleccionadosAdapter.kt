package com.example.gsangin.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gsangin.R

class ProductosSeleccionadosAdapter(private val productosConCantidad: List<Pair<ProductoSQLiteModel, Int>>) :
    RecyclerView.Adapter<ProductosSeleccionadosAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productoID: TextView = itemView.findViewById(R.id.productoID)
        val claveTextView: TextView = itemView.findViewById(R.id.claveTextView)
        val nombreProductoTextView: TextView = itemView.findViewById(R.id.nombreProductoTextView)
        val precioTextView: TextView = itemView.findViewById(R.id.precioTextView)
        val ivaTextView: TextView = itemView.findViewById(R.id.ivaTextView)
        val iepsTextView: TextView = itemView.findViewById(R.id.iepsTextView)
        val cantidadTextView: TextView = itemView.findViewById(R.id.cantidadTextView)
      //  val cantidadEditText: EditText = itemView.findViewById(R.id.cantidadEditText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto_seleccionado, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (producto, cantidad) = productosConCantidad[position]

        // Vincula los datos a las vistas
        holder.productoID.text = producto.id.toString()
        holder.claveTextView.text = producto.clave
        holder.nombreProductoTextView.text = producto.nombre
        holder.precioTextView.text = producto.precio.toString()
        holder.ivaTextView.text = producto.iva.toString()
        holder.iepsTextView.text = producto.ieps.toString()
        holder.cantidadTextView.text = cantidad.toString()

        // Puedes configurar listeners o cualquier lógica adicional aquí
    }

    override fun getItemCount(): Int {
        return productosConCantidad.size
    }
}
