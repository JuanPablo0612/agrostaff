package com.juanpablo0612.agrostaff.ui.blocks.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.juanpablo0612.agrostaff.data.blocks.BlocksRepository
import com.juanpablo0612.agrostaff.domain.models.Block

class BlockDetailViewModel(
    private val blocksRepository: BlocksRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var uiState by mutableStateOf(BlockDetailUiState())
        private set


}

data class BlockDetailUiState(
    val block: Block? = null,
    val isLoading: Boolean = false,
)

sealed class BlockDetailError(open val message: String?) {
    data class LoadFailed(override val message: String?): BlockDetailError(message)
}