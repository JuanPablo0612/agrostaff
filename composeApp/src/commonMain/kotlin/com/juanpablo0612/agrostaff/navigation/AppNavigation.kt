package com.juanpablo0612.agrostaff.navigation

import agrostaff.composeapp.generated.resources.Res
import agrostaff.composeapp.generated.resources.beds_title
import agrostaff.composeapp.generated.resources.blocks_title
import agrostaff.composeapp.generated.resources.users_title
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.People
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.juanpablo0612.agrostaff.ui.auth.sign_in.signInDestination
import com.juanpablo0612.agrostaff.ui.beds.add.AddBedDestination
import com.juanpablo0612.agrostaff.ui.beds.add.addBedDestination
import com.juanpablo0612.agrostaff.ui.beds.add.navigateToAddBed
import com.juanpablo0612.agrostaff.ui.beds.list.BedListDestination
import com.juanpablo0612.agrostaff.ui.beds.list.bedListDestination
import com.juanpablo0612.agrostaff.ui.blocks.add.AddBlockDestination
import com.juanpablo0612.agrostaff.ui.blocks.add.addBlockDestination
import com.juanpablo0612.agrostaff.ui.blocks.add.navigateToAddBlock
import com.juanpablo0612.agrostaff.ui.blocks.list.BlockListDestination
import com.juanpablo0612.agrostaff.ui.blocks.list.blockListDestination
import com.juanpablo0612.agrostaff.ui.users.add.AddUserDestination
import com.juanpablo0612.agrostaff.ui.users.add.addUserDestination
import com.juanpablo0612.agrostaff.ui.users.add.navigateToAddUser
import com.juanpablo0612.agrostaff.ui.users.list.UserListDestination
import com.juanpablo0612.agrostaff.ui.users.list.userListDestination
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

private data class BottomNavItem(
    val route: String,
    val label: StringResource,
    val icon: ImageVector,
    val onNavigate: () -> Unit,
)

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomNavItems = listOf(
        BottomNavItem(
            route = BlockListDestination::class.qualifiedName!!,
            label = Res.string.blocks_title,
            icon = Icons.Outlined.Dashboard,
            onNavigate = {
                navController.navigate(BlockListDestination) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
        ),
        BottomNavItem(
            route = BedListDestination::class.qualifiedName!!,
            label = Res.string.beds_title,
            icon = Icons.AutoMirrored.Outlined.List,
            onNavigate = {
                navController.navigate(BedListDestination) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
        ),
        BottomNavItem(
            route = UserListDestination::class.qualifiedName!!,
            label = Res.string.users_title,
            icon = Icons.Outlined.People,
            onNavigate = {
                navController.navigate(UserListDestination) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
        ),
    )

    val bottomRoutes = bottomNavItems.map { it.route }
    val shouldShowBottomBar = currentRoute in bottomRoutes

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                shouldShowBottomBar,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        val selected = currentRoute == item.route
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                if (!selected) {
                                    item.onNavigate()
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = stringResource(item.label)
                                )
                            },
                            label = {
                                Text(text = stringResource(item.label))
                            }
                        )
                    }
                }
            }
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BlockListDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            blockListDestination(
                onNavigateToAddBlock = {
                    navController.navigateToAddBlock()
                }
            )
            bedListDestination(
                onNavigateToAddBed = {
                    navController.navigateToAddBed()
                }
            )
            addBlockDestination(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
            addBedDestination(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
            signInDestination(onNavigateToPasswordRecovery = {}, onNavigateToSignUp = {})
            userListDestination(
                onNavigateToAddUser = {
                    navController.navigateToAddUser()
                }
            )
            addUserDestination(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
