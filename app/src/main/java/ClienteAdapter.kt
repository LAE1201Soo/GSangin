import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gsangin.ClienteSQLiteModel
import com.example.gsangin.R

class ClienteAdapter(private val clientes: List<ClienteSQLiteModel>) :
    RecyclerView.Adapter<ClienteAdapter.ViewHolder>() {

    // Interfaz para manejar los clics en los elementos
    interface OnItemClickListener {
        fun onItemClick(cliente: ClienteSQLiteModel)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.listener = onItemClickListener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idTextView: TextView = itemView.findViewById(R.id.ID)
        val nombreTextView: TextView = itemView.findViewById(R.id.nombreTextView)
        val razonSocialTextView: TextView = itemView.findViewById(R.id.razonSocialTextView)
        val calleTextView: TextView = itemView.findViewById(R.id.calle)
        val CPTextView: TextView = itemView.findViewById(R.id.CP)
        val CiudadTextView: TextView = itemView.findViewById(R.id.Ciudad)
        val EstadoTextView: TextView = itemView.findViewById(R.id.Estado)
        val NumeroTextView: TextView = itemView.findViewById(R.id.Numero)
        val TelTextView: TextView = itemView.findViewById(R.id.Tel)

        init {
            // Agrega un clic al elemento del RecyclerView
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(clientes[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_cliente, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cliente = clientes[position]
        // Vincula los datos del cliente a las vistas en el elemento de la lista
        holder.idTextView.text= cliente.id.toString()
        holder.nombreTextView.text = cliente.nombre
        holder.razonSocialTextView.text = cliente.razonSocial
        holder.calleTextView.text=cliente.calle
        holder.CPTextView.text=cliente.cp
        holder.CiudadTextView.text=cliente.ciudad
        holder.EstadoTextView.text=cliente.estado
        holder.NumeroTextView.text=cliente.numero
        holder.TelTextView.text=cliente.tel
    }

    override fun getItemCount(): Int {
        return clientes.size
    }
}
