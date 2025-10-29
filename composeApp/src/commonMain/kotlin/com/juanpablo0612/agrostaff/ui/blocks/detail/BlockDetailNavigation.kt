package com.juanpablo0612.agrostaff.ui.blocks.detail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data class BlockDetailDestination(
    val id: Int
)

fun NavController.navigateToBlockDetail(blockId: Int) {
    navigate(BlockDetailDestination(blockId))
}

fun NavGraphBuilder.blockDetailDestination(
    onNavigateBack: () -> Unit,
) {
    composable<BlockDetailDestination> {
        BlockDetailScreen(onNavigateBack = onNavigateBack)
    }
}
