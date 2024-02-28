package dataModel.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "facturas")
data class DataFactura(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var descEstado: String,
    var importeOrdenacion: Double,
    var fecha: String
)
