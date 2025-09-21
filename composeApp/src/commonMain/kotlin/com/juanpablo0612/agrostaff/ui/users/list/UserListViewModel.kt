package com.juanpablo0612.agrostaff.ui.users.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanpablo0612.agrostaff.data.users.UsersRepository
import com.juanpablo0612.agrostaff.data.users.model.IdentityDocumentType
import kotlinx.coroutines.launch

class UserListViewModel(private val usersRepository: UsersRepository) : ViewModel() {
    var uiState by mutableStateOf(UserListUiState())
        private set

    init {
        loadUsers()
    }

    private fun loadUsers() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)

            val usersResult = usersRepository.getAllUsers()

            uiState = usersResult.fold(
                onSuccess = { users ->
                    val userListItems = users.map { user ->
                        UserListItem(
                            id = user.id,
                            name = "${user.firstName} ${user.lastName}",
                            identityDocumentType = user.identityDocumentType,
                            identityDocumentNumber = user.identityDocumentNumber
                        )
                    }
                    uiState.copy(users = userListItems, isLoading = false, error = null)
                },
                onFailure = { error ->
                    uiState.copy(users = emptyList(), isLoading = false, error = error.message)
                }
            )
        }
    }
}

data class UserListUiState(
    val users: List<UserListItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)

data class UserListItem(
    val id: Int,
    val name: String,
    val identityDocumentType: IdentityDocumentType,
    val identityDocumentNumber: Long
)