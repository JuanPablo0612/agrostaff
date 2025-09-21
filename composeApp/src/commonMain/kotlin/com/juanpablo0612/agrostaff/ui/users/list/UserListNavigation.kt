package com.juanpablo0612.agrostaff.ui.users.list

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object UserListDestination

fun NavGraphBuilder.userListDestination(
    onNavigateToUserDetail: (userId: Int) -> Unit
) {
     composable<UserListDestination> {
         UserListScreen(onNavigateToUserDetail = onNavigateToUserDetail)
     }
}