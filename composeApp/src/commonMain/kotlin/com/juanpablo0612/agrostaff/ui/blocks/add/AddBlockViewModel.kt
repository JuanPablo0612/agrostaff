package com.juanpablo0612.agrostaff.ui.blocks.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanpablo0612.agrostaff.data.blocks.BlocksRepository
import com.juanpablo0612.agrostaff.domain.models.Block
import kotlinx.coroutines.launch

class AddBlockViewModel(
    private val blocksRepository: BlocksRepository,
) : ViewModel() {
    var uiState by mutableStateOf(AddBlockUiState())
        private set

    fun onNameChange(newName: String) {
        val sanitizedName = newName.trimStart()
        uiState = uiState.copy(
            name = sanitizedName,
            isValidName = true,
            errorMessage = null,
        )
    }

    fun onDescriptionChange(newDescription: String) {
        val sanitizedDescription = newDescription.trimStart()
        uiState = uiState.copy(
            description = sanitizedDescription,
            isValidDescription = true,
            errorMessage = null,
        )
    }

    fun onSave() {
        if (uiState.isLoading) return

        uiState = uiState.copy(
            name = uiState.name.trim(),
            description = uiState.description.trim(),
        )

        if (!validateFields()) return

        val block = Block(
            name = uiState.name,
            description = uiState.description,
        )

        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage = null)
            val result = blocksRepository.createBlock(block)
            uiState = if (result.isSuccess) {
                AddBlockUiState(isBlockAdded = true)
            } else {
                uiState.copy(
                    isLoading = false,
                    errorMessage = result.exceptionOrNull()?.message ?: "An unexpected error occurred",
                )
            }
        }
    }

    fun onBlockHandled() {
        uiState = uiState.copy(isBlockAdded = false)
    }

    private fun validateFields(): Boolean {
        val isValidName = uiState.name.isNotBlank()
        val isValidDescription = uiState.description.isNotBlank()

        uiState = uiState.copy(
            isValidName = isValidName,
            isValidDescription = isValidDescription,
        )

        return isValidName && isValidDescription
    }
}

data class AddBlockUiState(
    val name: String = "",
    val isValidName: Boolean = true,
    val description: String = "",
    val isValidDescription: Boolean = true,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isBlockAdded: Boolean = false,
)
