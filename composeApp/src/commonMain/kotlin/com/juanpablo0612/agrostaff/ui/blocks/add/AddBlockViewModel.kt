package com.juanpablo0612.agrostaff.ui.blocks.add

data class AddBlockUiState(
    val name: String = "",
    val isValidName: Boolean = true,
    val description: String = "",
    val isValidDescription: Boolean = true,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isBlockAdded: Boolean = false
)