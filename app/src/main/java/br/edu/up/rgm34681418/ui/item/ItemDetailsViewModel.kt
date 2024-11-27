package br.edu.up.rgm34681418.ui.item

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.up.rgm34681418.data.ItemsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ItemDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: ItemsRepository
) : ViewModel() {

    private val itemId: Int = checkNotNull(savedStateHandle[ItemDetailsDestination.itemIdArg])

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
    val uiState: StateFlow<ItemDetailsUiState> =
        repository.observarItemPorId(itemId)
            .filterNotNull()
            .map {
                ItemDetailsUiState(itemDetails = it.converterParaDetalhes())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ItemDetailsUiState()
            )
    fun reduceQuantityByOne() {
        viewModelScope.launch {
            val currentItem = uiState.value.itemDetails.converterParaItem()
            if (currentItem.quantity > 0) {
                repository.atualizarItem(currentItem.copy(quantity = currentItem.quantity - 1))
            }
        }
    }

    suspend fun deleteItem() {
        repository.removerItem(uiState.value.itemDetails.converterParaItem())
    }
}

data class ItemDetailsUiState(
    val outOfStock: Boolean = true,
    val itemDetails: DetalhesItem = DetalhesItem()
)
