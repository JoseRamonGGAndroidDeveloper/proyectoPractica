package viewModel.view


import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.a.R
import com.example.a.databinding.ActivityFiltrarFacturasBinding
import kotlinx.coroutines.launch
import viewModel.FacturasViewModel
import java.util.Calendar
import java.util.Locale


class FiltrarFacturasActivity : AppCompatActivity() {

    val vm: FacturasViewModel by viewModels { FacturasViewModel.FacturasViewModelFactory(application) }


    val ano = Calendar.getInstance()[Calendar.YEAR]
    val mes = Calendar.getInstance()[Calendar.MONTH]
    val dia = Calendar.getInstance()[Calendar.DAY_OF_MONTH]

    private var fechaDesde: Calendar? = null
    private var fechaHasta: Calendar? = null

    //filtrarBinding
    private lateinit var filtrarBinding: ActivityFiltrarFacturasBinding


    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        filtrarBinding = ActivityFiltrarFacturasBinding.inflate(layoutInflater)
        setContentView(filtrarBinding.root)

        //Selector fechaDesde
        selectorFechaDesde()

        //Selector fechaHasta
        selectorFechaHasta()

        //Eliminar filtros
        eliminarFiltros()

        //Cerrar pestaña sin guardar
        cerrarActivitySinCambios()

        MaxSlider()
    }

    fun selectorFechaDesde() {
        filtrarBinding.fechaBotonDesde.setOnClickListener() {
            val calendar = Calendar.getInstance()
            val year = calendar[Calendar.YEAR]
            val month = calendar[Calendar.MONTH]
            val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]

            val datePickerDialog = DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->
                val selectedDate = Calendar.getInstance()

                selectedDate[Calendar.YEAR] = year
                selectedDate[Calendar.MONTH] = monthOfYear
                selectedDate[Calendar.DAY_OF_MONTH] = dayOfMonth

                // Verificar si se ha seleccionado una fecha hasta y si la fecha desde es mayor que la fecha hasta
                if (fechaHasta != null && selectedDate > fechaHasta) {
                    Toast.makeText(
                        this,
                        this.getString(R.string.txtToasFechaDeste),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    // Establecer la fecha seleccionada en el botón y almacenarla
                    filtrarBinding.fechaBotonDesde.text = SimpleDateFormat(
                        "dd/MM/yyyy",
                        Locale.getDefault()
                    ).format(selectedDate.time)
                    fechaDesde = selectedDate
                }
            }, year, month, dayOfMonth)

            // Mostrar el DatePickerDialog
            datePickerDialog.show()
        }

    }

    fun selectorFechaHasta() {
        filtrarBinding.fechaBotonHasta.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar[Calendar.YEAR]
            val month = calendar[Calendar.MONTH]
            val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]

            val datePickerDialog = DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->
                val selectedDate = Calendar.getInstance()

                selectedDate[Calendar.YEAR] = year
                selectedDate[Calendar.MONTH] = monthOfYear
                selectedDate[Calendar.DAY_OF_MONTH] = dayOfMonth

                // Verificar si se ha seleccionado una fecha desde y si la fecha hasta es menor que la fecha desde
                if (fechaDesde != null && selectedDate < fechaDesde) {
                    Toast.makeText(
                        this,
                        this.getString(R.string.txtToasFechaHasta),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    // Establecer la fecha seleccionada en el botón y almacenarla
                    filtrarBinding.fechaBotonHasta.text = SimpleDateFormat(
                        "dd/MM/yyyy",
                        Locale.getDefault()
                    ).format(selectedDate.time)
                    fechaHasta = selectedDate
                }
            }, year, month, dayOfMonth)

            // Mostrar el DatePickerDialog
            datePickerDialog.show()
        }
    }

    fun MaxSlider() {
        //  filtrarBinding.SlideFiltrarF.value = vm.ObtenerMaxFactura()

        lifecycleScope.launch {
            val valorMaximo = vm.obtenerMayorImporte()
            filtrarBinding.SlideFiltrarF.valueTo = valorMaximo.toFloat()
        }

        filtrarBinding.SlideFiltrarF.addOnChangeListener() { _, value, _ ->
            filtrarBinding.tvRangoSlide.text =
                String.format("0 - %.2f" + getString(R.string.simboloMonetario), value)
        }


    }

    fun eliminarFiltros() {
        filtrarBinding.btnEliminarFiltrosFiltrarF.setOnClickListener() {

            //eliminar checkBoxes
            desmarcarCheckBoxes()

            //Poner el thumb en la posición inicial
            sliderValorDefault()

            //textView de min/max a 0
            marcadorSliderDefault()

            //botones de fecha a por defecto
            btnFechasPorDefecto()

        }
    }

    fun btnFechasPorDefecto() {
        filtrarBinding.fechaBotonDesde.setText(this.getString(R.string.txtBtnDesdeHasta))
        filtrarBinding.fechaBotonHasta.setText(this.getString(R.string.txtBtnDesdeHasta))
    }

    fun sliderValorDefault() {
        filtrarBinding.SlideFiltrarF.value = 0f
    }

    fun desmarcarCheckBoxes() {
        filtrarBinding.cbPagadas.setChecked(false);
        filtrarBinding.cbAnuladas.setChecked(false);
        filtrarBinding.cbCuotaFija.setChecked(false);
        filtrarBinding.cbPlanDePago.setChecked(false);
        filtrarBinding.cbPendienteDePago.setChecked(false);
    }

    fun marcadorSliderDefault() {
        filtrarBinding.tvRangoSlide.setText(this.getString(R.string.txtTvSlide))
    }

    fun cerrarActivitySinCambios() {
        filtrarBinding.closeButton.setOnClickListener() {
            finish()
        }
    }

    fun showFechaDesdeError() {
        Toast.makeText(
            this,
            this.getString(R.string.ErrorFacturas),
            Toast.LENGTH_SHORT
        ).show()
    }
}




