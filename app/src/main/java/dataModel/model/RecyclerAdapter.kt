package dataModel.model

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

import com.example.a.R
import viewModel.ViewHolder

class RecyclerAdapter : RecyclerView.Adapter<ViewHolder>() {

    var listaFacturas: MutableList<DataFactura> = ArrayList()

    fun RecyclerAdapter(listaFacturas: MutableList<DataFactura>) {
        this.listaFacturas = listaFacturas
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listaFacturas[position]
        holder.bind(item)

        holder.itemView.setOnClickListener {
            showPopUp(holder.itemView.context)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_factura, parent, false))


    }

    override fun getItemCount(): Int {
        return listaFacturas.size
    }

    private fun showPopUp(context: Context) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.popup_layout)

        val botonCerrar = dialog.findViewById<Button>(R.id.botonCerrarPopUp)

        botonCerrar.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}