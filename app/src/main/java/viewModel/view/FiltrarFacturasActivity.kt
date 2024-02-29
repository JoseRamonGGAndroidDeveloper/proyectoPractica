package viewModel.view


import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a.R
import com.example.a.databinding.ActivityFiltrarFacturasBinding
import java.util.Calendar


class FiltrarFacturasActivity : AppCompatActivity() {



    val ano = Calendar.getInstance()[Calendar.YEAR]
    val mes = Calendar.getInstance()[Calendar.MONTH]
    val dia = Calendar.getInstance()[Calendar.DAY_OF_MONTH]

    var fechaDesde = Calendar.getInstance()
    var fechaHasta = Calendar.getInstance()

    var slideValue = 99f

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

            val dPdDesde = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { _, mAno, mMes, mDia ->
                    fechaDesde = Calendar.getInstance()
                    filtrarBinding.fechaBotonDesde.text =
                        String.format("%d/%02d/%d", mDia, mMes + 1, mAno)
                },
                ano,
                mes,
                dia
            )
            dPdDesde.show()

        }

    }

    fun selectorFechaHasta() {
        filtrarBinding.fechaBotonHasta.setOnClickListener() {
            val dPdHasta = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { _, mAno, mMes, mDia ->
                    fechaHasta= Calendar.getInstance()
                    filtrarBinding.fechaBotonHasta.text =
                        String.format("%d/%02d/%d", mDia, mMes + 1, mAno)

                },
                ano,
                mes,
                dia
            )
            dPdHasta.show()
        }
    }

    fun MaxSlider(){
        filtrarBinding.SlideFiltrarF.value = slideValue
        filtrarBinding.SlideFiltrarF.addOnChangeListener(){ _,value, _ ->
            filtrarBinding.tvRangoSlide.text =
                String.format("0 - %.2f"+getString(R.string.simboloMonetario),value)
        }


    }

    fun comprobarFechas(){
        if (fechaDesde.after(fechaHasta)) {
            // La fecha seleccionada en btnDesde es posterior a la seleccionada en btnHasta, puedes mostrar un mensaje de error
            Toast.makeText(this, this.getString(R.string.ErrorFacturas), Toast.LENGTH_SHORT).show()
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




