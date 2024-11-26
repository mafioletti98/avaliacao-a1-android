package br.edu.up.rgm34681418.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    // Inserção de um item na tabela. Ignora conflitos.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    @Update
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)

    // Recupera um item pelo ID.
    @Query("SELECT * FROM items WHERE id = :id")
    fun getItemById(id: Int): Flow<Item>

    // Recupera todos os itens, ordenados alfabeticamente pelo nome.
    @Query("SELECT * FROM items ORDER BY name ASC")
    fun getAllItems(): Flow<List<Item>>
}
