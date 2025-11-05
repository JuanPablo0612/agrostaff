package com.juanpablo0612.agrostaff.ui.blocks.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.juanpablo0612.agrostaff.data.blocks.BlocksRepository
import com.juanpablo0612.agrostaff.domain.models.Block
import kotlinx.coroutines.launch

class BlockDetailViewModel(
    private val blocksRepository: BlocksRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var uiState by mutableStateOf(BlockDetailUiState())
        private set

    private val blockDetail = savedStateHandle.toRoute(BlockDetailDestination::class)

    init {
        loadBlock(blockDetail.id)
    }

    fun onEditingChange() {
        uiState = uiState.copy(isEditing = !uiState.isEditing)
    }

    fun onNameChange(newName: String) {
        val sanitizedName = newName.trimStart()
        uiState = uiState.copy(
            name = sanitizedName,
            isValidName = true,
            error = null
        )
    }

    fun onDescriptionChange(newDescription: String) {
        val sanitizedDescription = newDescription.trimStart()
        uiState = uiState.copy(
            description = sanitizedDescription,
            isValidDescription = true,
            error = null
        )
    }

    fun retry() {
        loadBlock(blockDetail.id)
    }

    private fun loadBlock(blockId: Int) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)
            val result = blocksRepository.getBlockById(blockId)
            result.fold(
                onSuccess = { block ->
                    uiState = uiState.copy(
                        blockId = block?.id,
                        name = block?.name.orEmpty(),
                        description = block?.description.orEmpty(),
                        isLoading = false
                    )
                },
                onFailure = {
                    uiState = uiState.copy(
                        isLoading = false,
                        error = BlockDetailError.LoadFailed(it.message)
                    )
                }
            )
        }
    }

    fun onUpdate() {
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
            uiState = uiState.copy(isLoading = true, error = null)
            val result = blocksRepository.createBlock(block)
            uiState = if (result.isSuccess) {
                BlockDetailUiState(isBlockUpdated = true)
            } else {
                uiState.copy(
                    isLoading = false,
                    error = BlockDetailError.UpdateFailed(result.exceptionOrNull()?.message ?: "An unexpected error occurred"),
                )
            }
        }
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

data class BlockDetailUiState(
    val blockId: Int? = null,
    val name: String = "",
    val isValidName: Boolean = true,
    val description: String = "",
    val isValidDescription: Boolean = true,
    val isEditing: Boolean = false,
    val isLoading: Boolean = false,
    val error: BlockDetailError? = null,
    val isBlockUpdated: Boolean = false,
)

sealed class BlockDetailError(open val message: String?) {
    data class LoadFailed(override val message: String?) : BlockDetailError(message)
    data class UpdateFailed(override val message: String?) : BlockDetailError(message)
}