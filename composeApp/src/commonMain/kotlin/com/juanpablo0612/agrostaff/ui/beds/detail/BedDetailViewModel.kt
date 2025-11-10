package com.juanpablo0612.agrostaff.ui.beds.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.juanpablo0612.agrostaff.data.beds.BedsRepository
import com.juanpablo0612.agrostaff.data.blocks.BlocksRepository
import com.juanpablo0612.agrostaff.domain.models.Bed
import com.juanpablo0612.agrostaff.ui.beds.components.BlockOption
import kotlinx.coroutines.launch

class BedDetailViewModel(
    private val blocksRepository: BlocksRepository,
    private val bedsRepository: BedsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var uiState by mutableStateOf(BedDetailUiState())
        private set

    private val bedDetail = savedStateHandle.toRoute(BedDetailDestination::class)

    init {
        loadBed(bedDetail.id)
    }

    fun onEditingChange() {
        uiState = uiState.copy(isEditing = !uiState.isEditing)
    }

    fun onBlockSelected(blockId: Int) {
        uiState = uiState.copy(
            selectedBlockId = blockId,
            isValidBlock = true,
            error = null
        )
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
        loadBed(bedDetail.id)
    }

    private fun loadBed(bedId: Int) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)
            val blocksResult = blocksRepository.getAllBlocks()

            if (blocksResult.isSuccess) {
                val bedResult = bedsRepository.getBedById(bedId)
                bedResult.fold(
                    onSuccess = { bed ->
                        val blockOptions = blocksResult.getOrElse { emptyList() }.map {
                            BlockOption(
                                id = it.id!!,
                                name = it.name
                            )
                        }
                        uiState = uiState.copy(
                            bedId = bed?.id,
                            blocks = blockOptions,
                            selectedBlockId = bed?.blockId,
                            name = bed?.name.orEmpty(),
                            description = bed?.description.orEmpty(),
                            isLoading = false
                        )
                    },
                    onFailure = {
                        uiState = uiState.copy(
                            isLoading = false,
                            error = BedDetailError.LoadFailed(it.message)
                        )
                    }
                )
            } else {
                uiState = uiState.copy(
                    isLoading = false,
                    error = BedDetailError.LoadFailed(blocksResult.exceptionOrNull()?.message ?: "Failed to load blocks")
                )
            }
        }
    }

    fun onUpdate() {
        if (uiState.isLoading) return

        uiState = uiState.copy(
            name = uiState.name.trim(),
            description = uiState.description.trim(),
        )

        if (!validateFields()) return

        val bed = Bed(
            id = uiState.bedId,
            blockId = uiState.selectedBlockId!!,
            name = uiState.name,
            description = uiState.description,
        )

        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)
            val result = bedsRepository.updateBed(uiState.bedId!!, bed)
            uiState = if (result.isSuccess) {
                BedDetailUiState(isBedUpdated = true)
            } else {
                uiState.copy(
                    isLoading = false,
                    error = BedDetailError.UpdateFailed(
                        result.exceptionOrNull()?.message ?: "An unexpected error occurred"
                    ),
                )
            }
        }
    }

    private fun validateFields(): Boolean {
        val isValidBlock = uiState.selectedBlockId != null
        val isValidName = uiState.name.isNotBlank()
        val isValidDescription = uiState.description.isNotBlank()

        uiState = uiState.copy(
            isValidBlock = isValidBlock,
            isValidName = isValidName,
            isValidDescription = isValidDescription,
        )

        return isValidName && isValidDescription && isValidBlock
    }
}

data class BedDetailUiState(
    val bedId: Int? = null,
    val blocks: List<BlockOption> = emptyList(),
    val selectedBlockId: Int? = null,
    val isValidBlock: Boolean = true,
    val name: String = "",
    val isValidName: Boolean = true,
    val description: String = "",
    val isValidDescription: Boolean = true,
    val isEditing: Boolean = false,
    val isLoading: Boolean = false,
    val error: BedDetailError? = null,
    val isBedUpdated: Boolean = false,
)

sealed class BedDetailError(open val message: String?) {
    data class LoadFailed(override val message: String?) : BedDetailError(message)
    data class UpdateFailed(override val message: String?) : BedDetailError(message)
}