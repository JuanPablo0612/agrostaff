package com.juanpablo0612.agrostaff.ui.users.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanpablo0612.agrostaff.data.users.UsersRepository
import com.juanpablo0612.agrostaff.data.users.model.AvailabilityStatus
import com.juanpablo0612.agrostaff.data.users.model.IdentityDocumentType
import com.juanpablo0612.agrostaff.data.users.model.UserRole
import kotlinx.coroutines.launch

class UserListViewModel(
    private val usersRepository: UsersRepository,
) : ViewModel() {
    var uiState by mutableStateOf(UserListUiState())
        private set

    init {
        loadUsers()
    }

    fun retry() {
        if (!uiState.isLoading) {
            loadUsers()
        }
    }

    fun consumeError() {
        if (uiState.error != null) {
            uiState = uiState.copy(error = null)
        }
    }

    fun deleteUser(userId: Int) {
        val userExists = uiState.users.any { it.id == userId }
        val isDeleting = uiState.deletingUserIds.contains(userId)
        if (!userExists || isDeleting) return

        viewModelScope.launch {
            uiState = uiState.copy(
                deletingUserIds = uiState.deletingUserIds + userId,
                error = null,
            )

            val result = usersRepository.deleteUser(userId)
            uiState = result.fold(
                onSuccess = {
                    uiState.copy(
                        users = uiState.users.filterNot { it.id == userId },
                        deletingUserIds = uiState.deletingUserIds - userId,
                        error = null,
                    )
                },
                onFailure = { error ->
                    uiState.copy(
                        deletingUserIds = uiState.deletingUserIds - userId,
                        error = UserListError.DeleteFailed(error.message),
                    )
                }
            )
        }
    }

    private fun loadUsers() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)

            val usersResult = usersRepository.getAllUsers()
            uiState = usersResult.fold(
                onSuccess = { users ->
                    val userListItems = users.map { user ->
                        UserListItem(
                            id = user.id!!,
                            name = "${user.firstName} ${user.lastName}",
                            identityDocumentType = user.identityDocumentType,
                            identityDocumentNumber = user.identityDocumentNumber,
                            phoneNumber = user.phoneNumber,
                            availability = user.availability,
                            role = user.role,
                        )
                    }
                    uiState.copy(
                        users = userListItems,
                        isLoading = false,
                        error = null,
                    )
                },
                onFailure = { error ->
                    uiState.copy(
                        users = emptyList(),
                        isLoading = false,
                        error = UserListError.LoadFailed(error.message),
                    )
                }
            )
        }
    }
}

data class UserListUiState(
    val users: List<UserListItem> = emptyList(),
    val isLoading: Boolean = false,
    val deletingUserIds: Set<Int> = emptySet(),
    val error: UserListError? = null,
)

data class UserListItem(
    val id: Int,
    val name: String,
    val identityDocumentType: IdentityDocumentType,
    val identityDocumentNumber: Long,
    val phoneNumber: Long,
    val availability: AvailabilityStatus,
    val role: UserRole,
)

sealed class UserListError(open val message: String?) {
    data class LoadFailed(override val message: String?) : UserListError(message)
    data class DeleteFailed(override val message: String?) : UserListError(message)
}
