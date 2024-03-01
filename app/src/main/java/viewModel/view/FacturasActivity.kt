package viewModel.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.infinum.retromock.Retromock
import com.example.a.R
import com.example.a.databinding.ActivityFacturasBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import dataModel.data.ApiRequest
import dataModel.model.DataFactura
import dataModel.model.RecyclerAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import viewModel.FacturasViewModel

class FacturasActivity : AppCompatActivity() {

    private lateinit var facturasBinding: ActivityFacturasBinding

    lateinit var xRecyclerView: RecyclerView

    var listFacturas: MutableList<DataFactura> = ArrayList()

    val adaptador: RecyclerAdapter = RecyclerAdapter()

    val url = "https://viewnextandroid3.wiremockapi.cloud/"

    val vm: FacturasViewModel by viewModels { FacturasViewModel.FacturasViewModelFactory(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        facturasBinding = ActivityFacturasBinding.inflate(layoutInflater)
        setContentView(facturasBinding.root)

        val isRetroFitSelected = intent.getBooleanExtra("isRetrofitSelected", true)

        facturasBinding.progressBarFacturas.visibility = View.VISIBLE

        //(FALTA CONTROLAR SI ESTÁ VACÍA LA LISTA DE FACTURAS)
        irAlFiltro()
        irAlManager()
        iniciarRecyclerView()



        if (isRetroFitSelected) {
            corrutinaFacturaRetrofit()
        } else {
            corrutinaFacturaRetroMock()
        }

    }

     fun irAlManager() {
        facturasBinding.btnConsumoFacturas.setOnClickListener() {
            val intent = Intent(this, ManagerActivity::class.java)
            startActivity(intent)
        }
    }

    fun irAlFiltro() {
        facturasBinding.btnIrAFiltrarF.setOnClickListener() {
            val intent = Intent(this, FiltrarFacturasActivity::class.java)
            startActivity(intent)
        }
    }

    fun getListaFacturas(): MutableList<DataFactura> {
        return listFacturas
    }

    fun iniciarRecyclerView() {
        xRecyclerView = facturasBinding.rvFacturas
        xRecyclerView.setHasFixedSize(true)
        xRecyclerView.layoutManager = LinearLayoutManager(this)
        adaptador.RecyclerAdapter(getListaFacturas())
        xRecyclerView.adapter = adaptador
    }

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getRetroMock(): Retromock {
        return Retromock.Builder()
            .retrofit(getRetrofit())
            .build()
    }

    private fun corrutinaFacturaRetroMock() {
        CoroutineScope(Dispatchers.IO).launch {
            val llamada = getRetroMock().create(ApiRequest::class.java).getFacturas()
            val result = llamada.body()
            runOnUiThread {
                if (llamada.isSuccessful) {
                    facturasBinding.progressBarFacturas.visibility = View.GONE
                    val factura = result?.facturas ?: emptyList()
                    vm.EliminarFacturasDeBDD()
                    vm.insertFacturasFromApi(factura)
                    adaptador.listaFacturas.addAll(factura)
                    adaptador.notifyDataSetChanged()
                } else {
                    showError()
                }
            }
        }
    }

    private fun corrutinaFacturaRetrofit() {
        CoroutineScope(Dispatchers.IO).launch {
            val llamada = getRetrofit().create(ApiRequest::class.java).getFacturas()
            val result = llamada.body()
            runOnUiThread {
                if (llamada.isSuccessful) {
                    facturasBinding.progressBarFacturas.visibility = View.GONE
                    val factura = result?.facturas ?: emptyList()
                    vm.EliminarFacturasDeBDD()
                    vm.insertFacturasFromApi(factura)
                    adaptador.listaFacturas.addAll(factura)
                    adaptador.notifyDataSetChanged()
                } else {
                    showError()
                }
            }
        }
    }

    private fun showError() {
        Toast.makeText(this, this.getString(R.string.ErrorGeneral), Toast.LENGTH_SHORT).show()
    }

}