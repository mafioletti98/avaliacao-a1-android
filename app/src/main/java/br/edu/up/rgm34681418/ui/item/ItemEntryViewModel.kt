package br.edu.up.rgm34681418.ui.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import br.edu.up.rgm34681418.data.Item
import br.edu.up.rgm34681418.data.ItemsRepository
import java.text.NumberFormat

class ItemEntryViewModel(private val repository: ItemsRepository) : ViewModel() {

    var uiState by mutableStateOf(ItemState())
        private set

    /**
     * Atualiza o estado da interface de usuário com os detalhes do item fornecidos.
     * Também realiza a validação dos campos.
     */
    fun atualizarEstado(detalhesItem: DetalhesItem) {
        uiState = ItemState(
            detalhesItem = detalhesItem,
            entradaValida = validarEntrada(detalhesItem)
        )
    }

    private fun validarEntrada(estado: DetalhesItem = uiState.detalhesItem): Boolean {
        return estado.nome.isNotBlank() && estado.preco.isNotBlank() && estado.quantidade.isNotBlank()
    }

    suspend fun salvarItem() {
        if (validarEntrada()) {
            repository.adicionarItem(uiState.detalhesItem.converterParaItem())
        }
    }
}

/**
 * Representa o estado da interface de usuário para um Item.
 */
data class ItemState(
    val detalhesItem: DetalhesItem = DetalhesItem(),
    val entradaValida: Boolean = false
)

data class DetalhesItem(
    val id: Int = 0,
    val nome: String = "",
    val preco: String = "",
    val quantidade: String = "",
)

/**
 * Função de extensão para converter DetalhesItem em Item.
 */
fun DetalhesItem.converterParaItem(): Item = Item(
    id = id,
    name = nome,
    price = preco.toDoubleOrNull() ?: 0.0,
    quantity = quantidade.toIntOrNull() ?: 0
)

/**
 * Função de extensão para formatar o preço de um Item.
 */
fun Item.precoFormatado(): String {
    return NumberFormat.getCurrencyInstance().format(price)
}

/**
 * Função de extensão para converter um Item em ItemState.
 */
fun Item.converterParaEstado(entradaValida: Boolean = false): ItemState = ItemState(
    detalhesItem = this.converterParaDetalhes(),
    entradaValida = entradaValida
)

/**
 * Função de extensão para converter um Item em DetalhesItem.
 */
fun Item.converterParaDetalhes(): DetalhesItem = DetalhesItem(
    id = id,
    nome = name,
    preco = price.toString(),
    quantidade = quantity.toString()
)
