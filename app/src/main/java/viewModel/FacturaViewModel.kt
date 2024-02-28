package viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import dataModel.model.DataFactura
import dataModel.data.FacturaDao
import dataModel.data.FacturaDataBase

class FacturasViewModel(application: Application) : AndroidViewModel(application) {
    private val facturaDao: FacturaDao
    //private val database: FacturaDataBase = Room.databaseBuilder(
    //    application.applicationContext,
    //    FacturaDataBase::class.java, "facturas"
    //).build()
    private val database: FacturaDataBase by lazy{FacturaDataBase.getDatabase(application)}

    init {
        facturaDao = database.facturaDao()
    }

    fun insertFacturasFromApi(facturas: List<DataFactura>) {
        viewModelScope.launch {
            facturaDao.insertFacturas(facturas)
        }
    }
    fun EliminarFacturasDeBDD() {
        viewModelScope.launch {
            facturaDao.eliminarBaseDeDatos()
        }
    }

    fun getImporteMasAlta() : Double{
        var importeMax : Double = 0.0
        viewModelScope.launch {
          importeMax = facturaDao.mayorImporte()
        }
        return importeMax
    }

    fun getAllFacturas(): LiveData<List<DataFactura>> {
        return facturaDao.getAllFacturas()
    }

    class FacturasViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
        override fun<T : ViewModel> create(modelClass:Class<T>): T {
            return  FacturasViewModel(application) as T
        }
    }
}