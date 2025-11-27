package com.juanpablo0612.agrostaff.ui.users.detail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data class UserDetailDestination(
    val id: Int
)

fun NavController.navigateToUserDetail(userId: Int) {
    navigate(UserDetailDestination(userId))
}

fun NavGraphBuilder.userDetailDestination(
    onNavigateBack: () -> Unit,
) {
    composable<UserDetailDestination> {
        UserDetailScreen(onNavigateBack = onNavigateBack, onUserUpdated = onNavigateBack)
    }
}
