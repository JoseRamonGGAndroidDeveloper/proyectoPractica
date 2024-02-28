package dataModel.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dataModel.model.DataFactura

@Database(entities = [DataFactura::class], version = 1, exportSchema = false)
abstract class FacturaDataBase : RoomDatabase() {
    abstract fun facturaDao(): FacturaDao

    companion object {
        @Volatile
        private var INSTANCE: FacturaDataBase? = null
        fun getDatabase(context: Context): FacturaDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FacturaDataBase::class.java,
                    "item_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE =instance
                return instance
            }
        }
    }

}