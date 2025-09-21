package com.juanpablo0612.agrostaff.ui.users.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.juanpablo0612.agrostaff.data.users.model.IdentityDocumentType
import com.juanpablo0612.agrostaff.ui.theme.AgroStaffTheme
import com.juanpablo0612.agrostaff.ui.users.list.components.UserListItemCard
import com.juanpablo0612.agrostaff.ui.users.list.components.UserListTopAppBar
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UserListScreen(
    viewModel: UserListViewModel = koinViewModel(),
    onNavigateToUserDetail: (Int) -> Unit
) {
    val uiState = viewModel.uiState

    UserListScreenContent(
        uiState = uiState,
        onNavigateToUserDetail = onNavigateToUserDetail
    )
}

@Composable
private fun UserListScreenContent(
    uiState: UserListUiState,
    onNavigateToUserDetail: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            UserListTopAppBar()
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.users, key = { user -> user.id }) { user ->
                    UserListItemCard(
                        userListItem = user,
                        onClick = {
                            onNavigateToUserDetail(user.id)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun UserListScreenContentPreview() {
    AgroStaffTheme {
        val uiState = UserListUiState(
            users = List(10) {
                UserListItem(
                    id = it,
                    name = "User $it",
                    identityDocumentType = IdentityDocumentType.CC,
                    identityDocumentNumber = it * 11111111L
                )
            }
        )

        UserListScreenContent(
            uiState = uiState,
            onNavigateToUserDetail = {}
        )
    }
}