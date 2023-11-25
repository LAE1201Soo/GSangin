package com.example.gsangin.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gsangin.R

class PedidoAdapter(private val pedidos: List<Pedido>) :
    RecyclerView.Adapter<PedidoAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val clienteIdTextView: TextView = itemView.findViewById(R.id.clienteIdTextView)
        //val clienteNombreTextView: TextView = itemView.findViewById(R.id.clienteNombreTextView)
        val subtotalTextView: TextView = itemView.findViewById(R.id.subtotalTextView)
        val totalTextView: TextView = itemView.findViewById(R.id.totalTextView)
        val recyclerViewProductos: RecyclerView = itemView.findViewById(R.id.recyclerViewProductos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pedido, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pedido = pedidos[position]

        // Vincula los datos a las vistas
        holder.clienteIdTextView.text = "Cliente ID: ${pedido.clienteId}"
        //holder.clienteNombreTextView.text = "Cliente: ${pedido.clienteNombre ?: "N/A"}"
        holder.subtotalTextView.text = "Subtotal: ${pedido.subtotal}"
        holder.totalTextView.text = "Total: ${pedido.total}"

        // Configura un LinearLayoutManager para el RecyclerView de productos
        val layoutManager = LinearLayoutManager(holder.recyclerViewProductos.context)
        holder.recyclerViewProductos.layoutManager = layoutManager

        // Crea un nuevo adaptador para mostrar los productos en este pedido
        val productosAdapter = ProductosSeleccionadosAdapter(pedido.productosConCantidad)
        holder.recyclerViewProductos.adapter = productosAdapter
    }

    override fun getItemCount(): Int {
        return pedidos.size
    }
}
