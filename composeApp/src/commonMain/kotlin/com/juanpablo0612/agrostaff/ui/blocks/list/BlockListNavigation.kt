package com.juanpablo0612.agrostaff.ui.blocks.list

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object BlockListDestination

fun NavGraphBuilder.blockListDestination(
    onNavigateToAddBlock: () -> Unit,
) {
    composable<BlockListDestination> {
        BlockListScreen(onNavigateToAddBlock = onNavigateToAddBlock)
    }
}
