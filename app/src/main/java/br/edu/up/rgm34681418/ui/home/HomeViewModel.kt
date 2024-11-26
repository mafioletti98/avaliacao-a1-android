package br.edu.up.rgm34681418.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.up.rgm34681418.data.Item
import br.edu.up.rgm34681418.data.ItemsRepository
import kotlinx.coroutines.flow.*

/**
 * ViewModel responsável por recuperar todos os itens do banco de dados Room.
 */
class HomeViewModel(repository: ItemsRepository) : ViewModel() {

    companion object {
        private const val TEMPO_LIMITE_MS = 5_000L
    }

    val estadoUiHome: StateFlow<EstadoUiHome> =
        repository.observarTodosOsItens()
            .map { itens -> EstadoUiHome(listaItens = itens) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TEMPO_LIMITE_MS),
                initialValue = EstadoUiHome()
            )
}


data class EstadoUiHome(val listaItens: List<Item> = emptyList())
