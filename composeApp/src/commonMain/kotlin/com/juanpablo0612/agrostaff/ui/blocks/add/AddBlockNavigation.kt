package com.juanpablo0612.agrostaff.ui.blocks.add

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object AddBlockDestination

fun NavGraphBuilder.addBlockDestination(
    onNavigateBack: () -> Unit,
) {
    composable<AddBlockDestination> {
        AddBlockScreen(
            onNavigateBack = onNavigateBack,
            onBlockAdded = onNavigateBack,
        )
    }
}
