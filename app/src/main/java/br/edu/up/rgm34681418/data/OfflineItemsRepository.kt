package br.edu.up.rgm34681418.data

import kotlinx.coroutines.flow.Flow


class OfflineItemsRepository(private val itemDao: ItemDao) :

    ItemsRepository {

        override fun observarTodosOsItens(): Flow<List<Item>> =
            itemDao.getAllItems()

        override fun observarItemPorId(id: Int): Flow<Item?> =
            itemDao.getItemById(id)

        override suspend fun adicionarItem(item: Item) = itemDao.insert(item)

        override suspend fun removerItem(item: Item) = itemDao.delete(item)

        override suspend fun atualizarItem(item: Item) = itemDao.update(item)
}