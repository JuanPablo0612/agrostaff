package com.juanpablo0612.agrostaff.ui.beds.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanpablo0612.agrostaff.data.beds.BedsRepository
import com.juanpablo0612.agrostaff.data.blocks.BlocksRepository
import com.juanpablo0612.agrostaff.domain.models.Bed
import com.juanpablo0612.agrostaff.ui.beds.components.BlockOption
import kotlinx.coroutines.launch

class AddBedViewModel(
    private val bedsRepository: BedsRepository,
    private val blocksRepository: BlocksRepository,
) : ViewModel() {
    var uiState by mutableStateOf(AddBedUiState())
        private set

    init {
        loadBlocks()
    }

    fun onBlockSelected(blockId: Int) {
        uiState = uiState.copy(
            selectedBlockId = blockId,
            isValidBlock = true,
            errorMessage = null,
        )
    }

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
        if (uiState.isLoading || uiState.isLoadingBlocks) return

        uiState = uiState.copy(
            name = uiState.name.trim(),
            description = uiState.description.trim(),
        )

        if (!validateFields()) return

        val selectedBlock = uiState.selectedBlockId ?: return
        val bed = Bed(
            blockId = selectedBlock,
            name = uiState.name,
            description = uiState.description,
        )

        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage = null)
            val result = bedsRepository.createBed(bed)
            uiState = if (result.isSuccess) {
                AddBedUiState(isBedAdded = true)
            } else {
                uiState.copy(
                    isLoading = false,
                    errorMessage = result.exceptionOrNull()?.message ?: "An unexpected error occurred",
                )
            }
        }
    }

    fun retryLoadBlocks() {
        if (uiState.isLoadingBlocks) return
        loadBlocks()
    }

    private fun loadBlocks() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoadingBlocks = true, blocks = emptyList(), blocksErrorMessage = null)

            val result = blocksRepository.getAllBlocks()
            uiState = result.fold(
                onSuccess = { blocks ->
                    val options = blocks.map { block ->
                        BlockOption(id = block.id!!, name = block.name)
                    }
                    uiState.copy(
                        blocks = options,
                        isLoadingBlocks = false,
                        blocksErrorMessage = null,
                    )
                },
                onFailure = { error ->
                    uiState.copy(
                        isLoadingBlocks = false,
                        blocksErrorMessage = error.message,
                    )
                }
            )
        }
    }

    private fun validateFields(): Boolean {
        val isValidName = uiState.name.isNotBlank()
        val isValidDescription = uiState.description.isNotBlank()
        val isValidBlock = uiState.selectedBlockId != null

        uiState = uiState.copy(
            isValidName = isValidName,
            isValidDescription = isValidDescription,
            isValidBlock = isValidBlock,
        )

        return isValidName && isValidDescription && isValidBlock
    }
}

data class AddBedUiState(
    val blocks: List<BlockOption> = emptyList(),
    val selectedBlockId: Int? = null,
    val isValidBlock: Boolean = true,
    val isLoadingBlocks: Boolean = false,
    val blocksErrorMessage: String? = null,
    val name: String = "",
    val isValidName: Boolean = true,
    val description: String = "",
    val isValidDescription: Boolean = true,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isBedAdded: Boolean = false,
)