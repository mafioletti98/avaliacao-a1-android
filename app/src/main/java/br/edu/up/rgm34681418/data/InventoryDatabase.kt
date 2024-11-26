package br.edu.up.rgm34681418.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Define a base de dados com a entidade Item e a versão do esquema.
@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class InventoryDatabase : RoomDatabase() {

    // Metodo abstrato que retorna o DAO para a tabela de itens.
    abstract fun itemDao(): ItemDao

    companion object {
        // Instância única da base de dados para evitar múltiplas conexões desnecessárias.
        @Volatile
        private var instancia: InventoryDatabase? = null

        fun obterBaseDeDados(contexto: Context): InventoryDatabase {
            return instancia ?: synchronized(this) {

                Room.databaseBuilder(
                    contexto.applicationContext,
                    InventoryDatabase::class.java,
                    "banco_itens"
                ).build().also { instancia = it }
            }
        }
    }
}
