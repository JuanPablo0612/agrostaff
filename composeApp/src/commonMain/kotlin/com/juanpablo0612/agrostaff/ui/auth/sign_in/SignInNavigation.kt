package com.juanpablo0612.agrostaff.ui.auth.sign_in

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object SignInDestination

fun NavGraphBuilder.signInDestination(
    onNavigateToPasswordRecovery: () -> Unit, onNavigateToSignUp: () -> Unit
) {
    composable<SignInDestination> {
        SignInScreen(onNavigateToPasswordRecovery = onNavigateToPasswordRecovery, onNavigateToSignUp = onNavigateToSignUp)
    }
}