package com.example.gsangin.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gsangin.R

class PrepedidoAdapter(
    private var productos: List<ProductoSQLiteModel>,
    private val cantidades: MutableList<Int>,
    private val productoClickListener: ProductoClickListener
) : RecyclerView.Adapter<PrepedidoAdapter.ViewHolder>() {

    // Interfaz para manejar los clics en los productos
    interface ProductoClickListener {
        fun onProductoClick(producto: ProductoSQLiteModel)
    }

    // ViewHolder para contener las vistas de los elementos del RecyclerView
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idTextView: TextView = itemView.findViewById(R.id.productoID)
        val claveTextView: TextView = itemView.findViewById(R.id.claveTextView)
        val nombreTextView: TextView = itemView.findViewById(R.id.nombreProductoTextView)
        val descripcionTextView: TextView = itemView.findViewById(R.id.descripcionTextView)
        val precioTextView: TextView = itemView.findViewById(R.id.precioTextView)
        val ivaTextView: TextView = itemView.findViewById(R.id.ivaTextView)
        val iepsTextView: TextView = itemView.findViewById(R.id.iepsTextView)
        val cantidadTextView: TextView = itemView.findViewById(R.id.cantidadTextView)
        val cantidadEditText: EditText = itemView.findViewById(R.id.cantidadEditText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_pedido1, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = productos[position]
        val cantidad = cantidades[position]

        // Configurar los datos del producto en el ViewHolder
        holder.idTextView.text = producto.id.toString()
        holder.claveTextView.text = producto.clave
        holder.nombreTextView.text = producto.nombre
        holder.descripcionTextView.text = producto.descripcion
        holder.precioTextView.text = producto.precio.toString()
        holder.ivaTextView.text = producto.iva.toString()
        holder.iepsTextView.text = producto.ieps.toString()

        // Configurar la cantidad
        holder.cantidadTextView.text = "Cantidad: $cantidad"
        holder.cantidadEditText.setText(cantidad.toString())

        // Manejar el clic en el elemento
        holder.itemView.setOnClickListener {
            productoClickListener.onProductoClick(producto)
        }
    }

    override fun getItemCount(): Int {
        return productos.size
    }

    // Este método permite actualizar la lista de productos en el adaptador
    fun updateList(newProductos: List<ProductoSQLiteModel>, newCantidades: List<Int>) {
        productos = newProductos
        cantidades.clear()
        cantidades.addAll(newCantidades)
        notifyDataSetChanged()
    }
}
