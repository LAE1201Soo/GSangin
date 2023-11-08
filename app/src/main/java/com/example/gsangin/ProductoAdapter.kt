package com.example.gsangin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gsangin.model.ProductoSQLiteModel

class ProductoAdapter(private val productos: List<ProductoSQLiteModel>) : RecyclerView.Adapter<ProductoAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idTextView: TextView = itemView.findViewById(R.id.productoID)
        val claveTextView: TextView = itemView.findViewById(R.id.claveTextView)
        val nombreTextView: TextView = itemView.findViewById(R.id.nombreProductoTextView)
        val descripcionTextView: TextView = itemView.findViewById(R.id.descripcionTextView)
        val precioTextView: TextView = itemView.findViewById(R.id.precioTextView)
        val ivaTextView: TextView = itemView.findViewById(R.id.ivaTextView)
        val iepsTextView: TextView = itemView.findViewById(R.id.iepsTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_producto, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = productos[position]
        holder.idTextView.text = producto.id.toString()
        holder.claveTextView.text = producto.clave
        holder.nombreTextView.text = producto.nombre
        holder.descripcionTextView.text = producto.descripcion
        holder.precioTextView.text = producto.precio.toString()
        holder.ivaTextView.text = producto.iva.toString()
        holder.iepsTextView.text = producto.ieps.toString()
    }

    override fun getItemCount(): Int {
        return productos.size
    }
}
