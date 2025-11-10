package com.juanpablo0612.agrostaff.ui.beds.detail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data class BedDetailDestination(
    val id: Int
)

fun NavController.navigateToBedDetail(bedId: Int) {
    navigate(BedDetailDestination(bedId))
}

fun NavGraphBuilder.bedDetailDestination(
    onNavigateBack: () -> Unit,
) {
    composable<BedDetailDestination> {
        BedDetailScreen(onNavigateBack = onNavigateBack, onBedUpdated = onNavigateBack)
    }
}
