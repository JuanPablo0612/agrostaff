package com.juanpablo0612.agrostaff.ui.blocks.detail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data class BlockListDestination(
    val id: Long
)

fun NavController.navigateToBlockDetail(blockId: Long) {
    navigate(BlockListDestination(blockId))
}

fun NavGraphBuilder.blockDetailDestination(
    onNavigateBack: () -> Unit,
) {
    composable<BlockListDestination> {
        BlockDetailScreen(onNavigateBack = onNavigateBack)
    }
}
