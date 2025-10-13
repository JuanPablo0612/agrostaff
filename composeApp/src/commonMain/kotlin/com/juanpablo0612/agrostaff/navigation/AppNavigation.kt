package com.juanpablo0612.agrostaff.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.juanpablo0612.agrostaff.ui.auth.sign_in.SignInDestination
import com.juanpablo0612.agrostaff.ui.auth.sign_in.signInDestination
import com.juanpablo0612.agrostaff.ui.blocks.add.AddBlockDestination
import com.juanpablo0612.agrostaff.ui.blocks.add.addBlockDestination
import com.juanpablo0612.agrostaff.ui.blocks.list.BlockListDestination
import com.juanpablo0612.agrostaff.ui.blocks.list.blockListDestination
import com.juanpablo0612.agrostaff.ui.users.list.userListDestination

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = BlockListDestination
    ) {
        blockListDestination(
            onNavigateToAddBlock = {
                navController.navigate(AddBlockDestination)
            }
        )
        addBlockDestination(
            onNavigateBack = {
                navController.popBackStack()
            }
        )
        signInDestination(onNavigateToPasswordRecovery = {}, onNavigateToSignUp = {})
        userListDestination(onNavigateToUserDetail = {})
    }
}
