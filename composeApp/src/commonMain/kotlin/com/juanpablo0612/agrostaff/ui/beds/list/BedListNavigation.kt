package com.juanpablo0612.agrostaff.ui.beds.list

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object BedListDestination

fun NavGraphBuilder.bedListDestination(
    onNavigateToAddBed: () -> Unit,
) {
    composable<BedListDestination> {
        BedListScreen(onNavigateToAddBed = onNavigateToAddBed)
    }
}
