package com.juanpablo0612.agrostaff.ui.users.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.juanpablo0612.agrostaff.data.users.UsersRepository
import com.juanpablo0612.agrostaff.data.users.model.AvailabilityStatus
import com.juanpablo0612.agrostaff.data.users.model.IdentityDocumentType
import com.juanpablo0612.agrostaff.data.users.model.UserRole
import com.juanpablo0612.agrostaff.domain.models.User
import kotlinx.coroutines.launch

class UserDetailViewModel(
    private val usersRepository: UsersRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var uiState by mutableStateOf(UserDetailUiState())
        private set

    private val userDetail = savedStateHandle.toRoute(UserDetailDestination::class)

    init {
        loadUser(userDetail.id)
    }

    fun onEditingChange() {
        uiState = uiState.copy(isEditing = !uiState.isEditing)
    }

    fun onFirstNameChange(newValue: String) {
        val sanitizedValue = newValue.trimStart()
        uiState = uiState.copy(
            firstName = sanitizedValue,
            isValidFirstName = true,
            error = null,
        )
    }

    fun onLastNameChange(newValue: String) {
        val sanitizedValue = newValue.trimStart()
        uiState = uiState.copy(
            lastName = sanitizedValue,
            isValidLastName = true,
            error = null,
        )
    }

    fun onPhoneNumberChange(newValue: String) {
        val digitsOnly = newValue.filter { it.isDigit() }
        uiState = uiState.copy(
            phoneNumber = digitsOnly,
            isValidPhoneNumber = true,
            error = null,
        )
    }

    fun onIdentityDocumentTypeChange(newValue: IdentityDocumentType) {
        uiState = uiState.copy(identityDocumentType = newValue)
    }

    fun onIdentityDocumentNumberChange(newValue: String) {
        val digitsOnly = newValue.filter { it.isDigit() }
        uiState = uiState.copy(
            identityDocumentNumber = digitsOnly,
            isValidIdentityDocumentNumber = true,
            error = null,
        )
    }

    fun onIdentityDocumentIssuedInChange(newValue: String) {
        val sanitizedValue = newValue.trimStart()
        uiState = uiState.copy(
            identityDocumentIssuedIn = sanitizedValue,
            isValidIdentityDocumentIssuedIn = true,
            error = null,
        )
    }

    fun onAvailabilityChange(newValue: AvailabilityStatus) {
        uiState = uiState.copy(availability = newValue)
    }

    fun onRoleChange(newValue: UserRole) {
        uiState = uiState.copy(role = newValue)
    }


    fun retry() {
        loadUser(userDetail.id)
    }

    private fun loadUser(userId: Int) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)

            val userResult = usersRepository.getUserById(userId)
            userResult.fold(
                onSuccess = {
                    uiState = uiState.copy(
                        user = it,
                        firstName = it?.firstName.orEmpty(),
                        lastName = it?.lastName.orEmpty(),
                        phoneNumber = it?.phoneNumber.toString(),
                        identityDocumentType = it?.identityDocumentType ?: IdentityDocumentType.CC,
                        identityDocumentNumber = it?.identityDocumentNumber.toString(),
                        identityDocumentIssuedIn = it?.identityDocumentIssuedIn.orEmpty(),
                        availability = it?.availability ?: AvailabilityStatus.ACTIVE,
                        role = it?.role ?: UserRole.WORKER,
                        isLoading = false
                    )
                },
                onFailure = {
                    uiState = uiState.copy(
                        isLoading = false,
                        error = UserDetailError.LoadFailed(it.message)
                    )
                }
            )
        }
    }

    fun onUpdate() {
        if (uiState.isLoading) return

        uiState = uiState.copy(
            firstName = uiState.firstName.trim(),
            lastName = uiState.lastName.trim(),
            identityDocumentIssuedIn = uiState.identityDocumentIssuedIn.trim(),
        )

        if (!validateFields()) return

        val user = uiState.user!!.copy(
            firstName = uiState.firstName,
            lastName = uiState.lastName,
            phoneNumber = uiState.phoneNumber.toLong(),
            identityDocumentType = uiState.identityDocumentType,
            identityDocumentNumber = uiState.identityDocumentNumber.toLong(),
            identityDocumentIssuedIn = uiState.identityDocumentIssuedIn,
            availability = uiState.availability,
            role = uiState.role,
        )

        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)
            val result = usersRepository.updateUser(uiState.user!!.id!!, user)
            uiState = if (result.isSuccess) {
                UserDetailUiState(isUserUpdated = true)
            } else {
                uiState.copy(
                    isLoading = false,
                    error = UserDetailError.UpdateFailed(
                        result.exceptionOrNull()?.message ?: "An unexpected error occurred"
                    ),
                )
            }
        }
    }

    private fun validateFields(): Boolean {
        val isValidFirstName = uiState.firstName.isNotBlank()
        val isValidLastName = uiState.lastName.isNotBlank()
        val isValidPhoneNumber =
            uiState.phoneNumber.isNotBlank() && uiState.phoneNumber.toLongOrNull() != null
        val isValidIdentityDocumentNumber =
            uiState.identityDocumentNumber.isNotBlank() && uiState.identityDocumentNumber.toLongOrNull() != null
        val isValidIdentityDocumentIssuedIn = uiState.identityDocumentIssuedIn.isNotBlank()

        uiState = uiState.copy(
            isValidFirstName = isValidFirstName,
            isValidLastName = isValidLastName,
            isValidPhoneNumber = isValidPhoneNumber,
            isValidIdentityDocumentNumber = isValidIdentityDocumentNumber,
            isValidIdentityDocumentIssuedIn = isValidIdentityDocumentIssuedIn,
        )

        return isValidFirstName &&
                isValidLastName &&
                isValidPhoneNumber &&
                isValidIdentityDocumentNumber &&
                isValidIdentityDocumentIssuedIn
    }
}

data class UserDetailUiState(
    val user: User? = null,
    val firstName: String = "",
    val isValidFirstName: Boolean = true,
    val lastName: String = "",
    val isValidLastName: Boolean = true,
    val phoneNumber: String = "",
    val isValidPhoneNumber: Boolean = true,
    val identityDocumentType: IdentityDocumentType = IdentityDocumentType.CC,
    val identityDocumentNumber: String = "",
    val isValidIdentityDocumentNumber: Boolean = true,
    val identityDocumentIssuedIn: String = "",
    val isValidIdentityDocumentIssuedIn: Boolean = true,
    val availability: AvailabilityStatus = AvailabilityStatus.ACTIVE,
    val role: UserRole = UserRole.WORKER,
    val isEditing: Boolean = false,
    val isLoading: Boolean = false,
    val error: UserDetailError? = null,
    val isUserUpdated: Boolean = false,
)

sealed class UserDetailError(open val message: String?) {
    data class LoadFailed(override val message: String?) : UserDetailError(message)
    data class UpdateFailed(override val message: String?) : UserDetailError(message)
}