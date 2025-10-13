package com.juanpablo0612.agrostaff.ui.beds.add

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object AddBedDestination

fun NavGraphBuilder.addBedDestination(
    onNavigateBack: () -> Unit,
) {
    composable<AddBedDestination> {
        AddBedScreen(
            onNavigateBack = onNavigateBack,
            onBedAdded = onNavigateBack,
        )
    }
}
