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




fun DetalhesItem.converterParaItem(): Item = Item(
    id = id,
    name = nome,
    price = preco.toDoubleOrNull() ?: 0.0,
    quantity = quantidade.toIntOrNull() ?: 0
)

fun Item.precoFormatado(): String {
    return NumberFormat.getCurrencyInstance().format(price)
}

fun Item.converterParaEstado(entradaValida: Boolean = false): ItemState = ItemState(
    detalhesItem = this.converterParaDetalhes(),
    entradaValida = entradaValida
)

fun Item.converterParaDetalhes(): DetalhesItem = DetalhesItem(
    id = id,
    nome = name,
    preco = price.toString(),
    quantidade = quantity.toString()
)
