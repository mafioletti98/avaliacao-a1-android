package br.edu.up.rgm34681418.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import br.edu.up.rgm34681418.InventoryApplication
import br.edu.up.rgm34681418.ui.home.HomeViewModel
import br.edu.up.rgm34681418.ui.item.ItemDetailsViewModel
import br.edu.up.rgm34681418.ui.item.ItemEditViewModel
import br.edu.up.rgm34681418.ui.item.ItemEntryViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Configuração do ItemEditViewModel
        initializer {
            ItemEditViewModel(
                createSavedStateHandle(),
                getInventoryApplication().container.itemsRepository
            )
        }
        // Configuração do ItemEntryViewModel
        initializer {
            ItemEntryViewModel(
                getInventoryApplication().container.itemsRepository
            )
        }
        // Configuração do ItemDetailsViewModel
        initializer {
            ItemDetailsViewModel(
                createSavedStateHandle(),
                getInventoryApplication().container.itemsRepository
            )
        }
        // Configuração do HomeViewModel
        initializer {
            HomeViewModel(
                getInventoryApplication().container.itemsRepository
            )
        }
    }

    /**
     * Função de extensão para obter a instância de [InventoryApplication]
     * a partir das [CreationExtras].
     */
    private fun CreationExtras.getInventoryApplication(): InventoryApplication {
        return this[AndroidViewModelFactory.APPLICATION_KEY] as InventoryApplication
    }
}
