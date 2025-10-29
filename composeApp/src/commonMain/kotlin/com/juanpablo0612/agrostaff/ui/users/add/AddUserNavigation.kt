package com.juanpablo0612.agrostaff.ui.users.add

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object AddUserDestination

fun NavController.navigateToAddUser() {
    navigate(AddUserDestination)
}

fun NavGraphBuilder.addUserDestination(
    onNavigateBack: () -> Unit,
) {
    composable<AddUserDestination> {
        AddUserScreen(
            onNavigateBack = onNavigateBack,
            onUserAdded = onNavigateBack,
        )
    }
}
