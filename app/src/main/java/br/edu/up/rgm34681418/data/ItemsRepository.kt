package br.edu.up.rgm34681418.data

import kotlinx.coroutines.flow.Flow

interface ItemsRepository {

    fun observarTodosOsItens(): Flow<List<Item>>

    fun observarItemPorId(id: Int): Flow<Item?>

    suspend fun adicionarItem(item: Item)

    suspend fun removerItem(item: Item)

    suspend fun atualizarItem(item: Item)
}
