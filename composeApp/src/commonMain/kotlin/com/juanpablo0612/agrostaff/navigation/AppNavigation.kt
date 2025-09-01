package com.juanpablo0612.agrostaff.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.juanpablo0612.agrostaff.ui.auth.sign_in.SignInDestination
import com.juanpablo0612.agrostaff.ui.auth.sign_in.signInDestination

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SignInDestination
    ) {
        signInDestination(onNavigateToPasswordRecovery = {}, onNavigateToSignUp = {})
    }
}