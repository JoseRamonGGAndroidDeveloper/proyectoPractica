package dataModel.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dataModel.model.DataFactura

@Dao
interface FacturaDao {
    @Query("SELECT * FROM facturas")
    fun getAllFacturas(): LiveData<List<DataFactura>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFacturas(facturas: List<DataFactura>)

    @Query("DELETE FROM facturas")
        suspend fun eliminarBaseDeDatos()

        @Query("SELECT MAX(importeOrdenacion)  FROM facturas")
        suspend fun mayorImporte() : Double
}
