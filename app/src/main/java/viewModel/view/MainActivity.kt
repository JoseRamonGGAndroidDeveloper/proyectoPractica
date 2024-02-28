package viewModel.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a.R
import com.example.a.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import dataModel.data.ApiRequest
import dataModel.model.DataFactura
import dataModel.model.RecyclerAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import viewModel.FacturasViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    lateinit var xRecyclerView: RecyclerView

    var listFacturas: MutableList<DataFactura> = ArrayList()

    val adaptador: RecyclerAdapter = RecyclerAdapter()

    val url = "https://viewnextandroid3.wiremockapi.cloud/"

    val vm: FacturasViewModel by viewModels {FacturasViewModel.FacturasViewModelFactory(application)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)





        //Boton para ir a la acitivty de filtrar facturas (FALTA CONTROLAR SI ESTÁ VACÍA LA LISTA DE FACTURAS)

        mainBinding.btnIrAFiltrarF.setOnClickListener() {
            val intent = Intent(this, FiltrarFacturasActivity::class.java)
            startActivity(intent)
        }


        iniciarRecyclerView()
        corrutinaFactura()
    }

    fun getListaFacturas(): MutableList<DataFactura>{
        return listFacturas
    }

    fun iniciarRecyclerView(){
        xRecyclerView = mainBinding.rvFacturas
        xRecyclerView.setHasFixedSize(true)
        xRecyclerView.layoutManager = LinearLayoutManager(this)
        adaptador.RecyclerAdapter(getListaFacturas())
        xRecyclerView.adapter = adaptador
    }

    fun getRetrofit():Retrofit{
        return Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build()
    }

    private fun corrutinaFactura(){
        CoroutineScope(Dispatchers.IO).launch {
            val llamada = getRetrofit().create(ApiRequest::class.java).getFacturas()
            val result = llamada.body()
            runOnUiThread{
                if (llamada.isSuccessful){
                    val factura = result?.facturas ?: emptyList()
                    vm.EliminarFacturasDeBDD()
                    vm.insertFacturasFromApi(factura)
                    adaptador.listaFacturas.addAll(factura)
                    adaptador.notifyDataSetChanged()
                }else{
                    showError()
                }
            }
        }
    }

    private fun showError() {
       Toast.makeText(this,this.getString(R.string.ErrorGeneral),Toast.LENGTH_SHORT).show()
    }

}