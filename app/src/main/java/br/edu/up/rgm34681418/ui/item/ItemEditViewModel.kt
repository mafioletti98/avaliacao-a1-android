package br.edu.up.rgm34681418.ui.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.up.rgm34681418.data.ItemsRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class ItemEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: ItemsRepository
) : ViewModel() {

    var itemUiState by mutableStateOf(ItemState())
        private set

    private val itemId: Int = checkNotNull(savedStateHandle[ItemEditDestination.itemIdArg])

    init {
        viewModelScope.launch {
            itemUiState = repository.observarItemPorId(itemId)
                .filterNotNull()
                .first()
                .converterParaEstado(true)
        }
    }

    private fun validateInput(uiState: DetalhesItem = itemUiState.detalhesItem): Boolean {
        return with(uiState) {
            nome.isNotBlank() && preco.isNotBlank() && quantidade.isNotBlank()
        }
    }

    fun updateUiState(detalhesItem: DetalhesItem) {
        itemUiState =
            ItemState(
                detalhesItem = detalhesItem, entradaValida = validateInput(detalhesItem)
            )
    }

    suspend fun updateItem() {
        if (validateInput(itemUiState.detalhesItem)) {
            repository.atualizarItem(itemUiState.detalhesItem.converterParaItem())
        }
    }
}
