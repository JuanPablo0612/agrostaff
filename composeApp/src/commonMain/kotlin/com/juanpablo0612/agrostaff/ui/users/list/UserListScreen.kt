package com.juanpablo0612.agrostaff.ui.users.list

import agrostaff.composeapp.generated.resources.Res
import agrostaff.composeapp.generated.resources.user_list_delete_error
import agrostaff.composeapp.generated.resources.user_list_empty_state
import agrostaff.composeapp.generated.resources.user_list_generic_error
import agrostaff.composeapp.generated.resources.user_list_retry_button
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.juanpablo0612.agrostaff.data.users.model.AvailabilityStatus
import com.juanpablo0612.agrostaff.data.users.model.IdentityDocumentType
import com.juanpablo0612.agrostaff.data.users.model.UserRole
import com.juanpablo0612.agrostaff.ui.theme.AgroStaffTheme
import com.juanpablo0612.agrostaff.ui.users.list.components.UserListItemCard
import com.juanpablo0612.agrostaff.ui.users.list.components.UserListTopAppBar
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UserListScreen(
    onNavigateToAddUser: () -> Unit,
    viewModel: UserListViewModel = koinViewModel(),
) {
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }

    val errorMessage = uiState.error?.let { error ->
        when (error) {
            is UserListError.LoadFailed -> error.message ?: stringResource(Res.string.user_list_generic_error)
            is UserListError.DeleteFailed -> error.message ?: stringResource(Res.string.user_list_delete_error)
        }
    }

    LaunchedEffect(errorMessage) {
        if (errorMessage != null) {
            snackbarHostState.showSnackbar(errorMessage)
            viewModel.consumeError()
        }
    }

    UserListScreenContent(
        uiState = uiState,
        onAddUser = onNavigateToAddUser,
        onDeleteUser = viewModel::deleteUser,
        onRetry = viewModel::retry,
        snackbarHostState = snackbarHostState,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun UserListScreenContent(
    uiState: UserListUiState,
    onAddUser: () -> Unit,
    onDeleteUser: (Int) -> Unit,
    onRetry: () -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    Scaffold(
        topBar = {
            UserListTopAppBar(onAddUser = onAddUser)
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { snackbarData ->
                Snackbar(snackbarData)
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                uiState.isLoading && uiState.users.isEmpty() -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                uiState.users.isEmpty() -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 32.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(text = stringResource(Res.string.user_list_empty_state))
                        if (uiState.error is UserListError.LoadFailed) {
                            Spacer(modifier = Modifier.height(16.dp))
                            FilledTonalButton(onClick = onRetry) {
                                Text(text = stringResource(Res.string.user_list_retry_button))
                            }
                        }
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = uiState.users,
                            key = { user -> user.id }
                        ) { user ->
                            val isDeleting = uiState.deletingUserIds.contains(user.id)
                            UserListItemCard(
                                userListItem = user,
                                onDelete = { onDeleteUser(user.id) },
                                isDeleting = isDeleting,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun UserListScreenContentPreview() {
    AgroStaffTheme {
        UserListScreenContent(
            uiState = UserListUiState(
                users = List(3) {
                    UserListItem(
                        id = it,
                        name = "User $it",
                        identityDocumentType = IdentityDocumentType.CC,
                        identityDocumentNumber = 1000000000L + it,
                        phoneNumber = 3200000000L + it,
                        availability = AvailabilityStatus.ACTIVE,
                        role = UserRole.WORKER,
                    )
                }
            ),
            onAddUser = {},
            onDeleteUser = {},
            onRetry = {},
            snackbarHostState = SnackbarHostState(),
        )
    }
}
