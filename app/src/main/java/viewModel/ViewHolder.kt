package viewModel

import android.graphics.Color
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.a.R
import com.example.a.databinding.ItemFacturaBinding
import dataModel.model.DataFactura

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var simboloMonetario = itemView.context.getString(R.string.simboloMonetario)
    private var bindingRecycler = ItemFacturaBinding.bind(view)



    fun bind(factura: DataFactura) {
        bindingRecycler.tvFecha.text = cambiarFormatoFecha(factura.fecha)

        bindingRecycler.tvImporteOrdenacion.text = cambiarFormatoMoneda(factura.importeOrdenacion)
        bindingRecycler.tvDescEstado.text = factura.descEstado

        bindingRecycler.tvDescEstado.setTextColor(Color.RED)
        // bindingRecycler.tvDescEstado.isVisible = false
        pendientePago(factura.descEstado)
    }



    fun pendientePago(estadoPago: String) {

        if (estadoPago.equals("Pagada", ignoreCase = true)) {
            bindingRecycler.tvDescEstado.isVisible = false
        }
    }

    fun cambiarFormatoMoneda(pago: Double): String {

        val pagoFormateado = pago.toString().replace(".", ",") + simboloMonetario
        return pagoFormateado
    }

    fun cambiarFormatoFecha(fecha: String): String {

        val fechaEnPartes = fecha.split("/")

        var dia = fechaEnPartes[0]
        var mes = fechaEnPartes[1]
        var anio = fechaEnPartes[2]


       when (mes) {
            "01" -> mes = "Ene"
            "02" -> mes = "Feb"
            "03" -> mes = "Mar"
            "04" -> mes = "Abr"
            "05" -> mes = "May"
            "06" -> mes = "Jun"
            "07" -> mes = "Jul"
            "08" -> mes = "Ago"
            "09" -> mes = "Sep"
            "10" -> mes = "Oct"
            "11" -> mes = "Nov"
            "12" -> mes = "Dic"
            else -> mes = "XX"
        }

        val fechaFormateada = dia+" "+mes+" "+anio
        return fechaFormateada
    }
}