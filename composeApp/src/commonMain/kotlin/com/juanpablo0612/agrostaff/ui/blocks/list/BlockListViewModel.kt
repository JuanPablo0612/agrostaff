package com.juanpablo0612.agrostaff.ui.blocks.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanpablo0612.agrostaff.data.blocks.BlocksRepository
import kotlinx.coroutines.launch

class BlockListViewModel(
    private val blocksRepository: BlocksRepository,
) : ViewModel() {
    var uiState by mutableStateOf(BlockListUiState())
        private set

    init {
        loadBlocks()
    }

    fun retry() {
        loadBlocks()
    }

    fun consumeError() {
        if (uiState.error != null) {
            uiState = uiState.copy(error = null)
        }
    }

    fun deleteBlock(blockId: Int) {
        val blockExists = uiState.blocks.any { it.id == blockId }
        val isDeleting = uiState.deletingBlockIds.contains(blockId)
        if (!blockExists || isDeleting) return

        viewModelScope.launch {
            uiState = uiState.copy(
                deletingBlockIds = uiState.deletingBlockIds + blockId,
                error = null,
            )

            val result = blocksRepository.deleteBlock(blockId)
            uiState = result.fold(
                onSuccess = {
                    uiState.copy(
                        blocks = uiState.blocks.filterNot { it.id == blockId },
                        deletingBlockIds = uiState.deletingBlockIds - blockId,
                        error = null,
                    )
                },
                onFailure = { error ->
                    uiState.copy(
                        deletingBlockIds = uiState.deletingBlockIds - blockId,
                        error = BlockListError.DeleteFailed(error.message),
                    )
                }
            )
        }
    }

    private fun loadBlocks() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)

            val result = blocksRepository.getAllBlocks()
            uiState = result.fold(
                onSuccess = { blocks ->
                    uiState.copy(
                        blocks = blocks
                            .mapNotNull { block ->
                                val id = block.id ?: return@mapNotNull null
                                BlockListItem(
                                    id = id,
                                    name = block.name,
                                    description = block.description,
                                )
                            },
                        isLoading = false,
                        error = null,
                    )
                },
                onFailure = { error ->
                    uiState.copy(
                        blocks = emptyList(),
                        isLoading = false,
                        error = BlockListError.LoadFailed(error.message),
                    )
                }
            )
        }
    }
}

data class BlockListUiState(
    val blocks: List<BlockListItem> = emptyList(),
    val isLoading: Boolean = false,
    val deletingBlockIds: Set<Int> = emptySet(),
    val error: BlockListError? = null,
)

data class BlockListItem(
    val id: Int,
    val name: String,
    val description: String,
)

sealed class BlockListError(open val message: String?) {
    data class LoadFailed(override val message: String?) : BlockListError(message)
    data class DeleteFailed(override val message: String?) : BlockListError(message)
}
