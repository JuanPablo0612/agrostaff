package com.juanpablo0612.agrostaff.ui.beds.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanpablo0612.agrostaff.data.beds.BedsRepository
import kotlinx.coroutines.launch

class BedListViewModel(
    private val bedsRepository: BedsRepository,
) : ViewModel() {
    var uiState by mutableStateOf(BedListUiState())
        private set

    init {
        loadBeds()
    }

    fun retry() {
        loadBeds()
    }

    fun consumeError() {
        if (uiState.error != null) {
            uiState = uiState.copy(error = null)
        }
    }

    fun deleteBed(bedId: Int) {
        val bedExists = uiState.beds.any { it.id == bedId }
        val isDeleting = uiState.deletingBedIds.contains(bedId)
        if (!bedExists || isDeleting) return

        viewModelScope.launch {
            uiState = uiState.copy(
                deletingBedIds = uiState.deletingBedIds + bedId,
                error = null,
            )

            val result = bedsRepository.deleteBed(bedId)
            uiState = result.fold(
                onSuccess = {
                    uiState.copy(
                        beds = uiState.beds.filterNot { it.id == bedId },
                        deletingBedIds = uiState.deletingBedIds - bedId,
                        error = null,
                    )
                },
                onFailure = { error ->
                    uiState.copy(
                        deletingBedIds = uiState.deletingBedIds - bedId,
                        error = BedListError.DeleteFailed(error.message),
                    )
                }
            )
        }
    }

    private fun loadBeds() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)

            val result = bedsRepository.getAllBeds()
            uiState = result.fold(
                onSuccess = { beds ->
                    uiState.copy(
                        beds = beds.mapNotNull { bed ->
                            val id = bed.id ?: return@mapNotNull null
                            BedListItem(
                                id = id,
                                name = bed.name,
                                description = bed.description,
                                blockId = bed.blockId,
                            )
                        },
                        isLoading = false,
                        error = null,
                    )
                },
                onFailure = { error ->
                    uiState.copy(
                        beds = emptyList(),
                        isLoading = false,
                        error = BedListError.LoadFailed(error.message),
                    )
                }
            )
        }
    }
}

data class BedListUiState(
    val beds: List<BedListItem> = emptyList(),
    val isLoading: Boolean = false,
    val deletingBedIds: Set<Int> = emptySet(),
    val error: BedListError? = null,
)

data class BedListItem(
    val id: Int,
    val name: String,
    val description: String,
    val blockId: Int,
)

sealed class BedListError(open val message: String?) {
    data class LoadFailed(override val message: String?) : BedListError(message)
    data class DeleteFailed(override val message: String?) : BedListError(message)
}
