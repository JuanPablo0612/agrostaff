package com.juanpablo0612.agrostaff.ui.users.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanpablo0612.agrostaff.data.users.UsersRepository
import com.juanpablo0612.agrostaff.data.users.model.AvailabilityStatus
import com.juanpablo0612.agrostaff.data.users.model.IdentityDocumentType
import com.juanpablo0612.agrostaff.data.users.model.UserRole
import com.juanpablo0612.agrostaff.domain.models.CreateUser
import kotlinx.coroutines.launch

class AddUserViewModel(
    private val usersRepository: UsersRepository,
) : ViewModel() {
    var uiState by mutableStateOf(AddUserUiState())
        private set

    fun onFirstNameChange(newValue: String) {
        val sanitizedValue = newValue.trimStart()
        uiState = uiState.copy(
            firstName = sanitizedValue,
            isValidFirstName = true,
            errorMessage = null,
        )
    }

    fun onLastNameChange(newValue: String) {
        val sanitizedValue = newValue.trimStart()
        uiState = uiState.copy(
            lastName = sanitizedValue,
            isValidLastName = true,
            errorMessage = null,
        )
    }

    fun onPhoneNumberChange(newValue: String) {
        val digitsOnly = newValue.filter { it.isDigit() }
        uiState = uiState.copy(
            phoneNumber = digitsOnly,
            isValidPhoneNumber = true,
            errorMessage = null,
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
            errorMessage = null,
        )
    }

    fun onIdentityDocumentIssuedInChange(newValue: String) {
        val sanitizedValue = newValue.trimStart()
        uiState = uiState.copy(
            identityDocumentIssuedIn = sanitizedValue,
            isValidIdentityDocumentIssuedIn = true,
            errorMessage = null,
        )
    }

    fun onAvailabilityChange(newValue: AvailabilityStatus) {
        uiState = uiState.copy(availability = newValue)
    }

    fun onRoleChange(newValue: UserRole) {
        uiState = uiState.copy(role = newValue)
    }

    fun onSave() {
        if (uiState.isLoading) return

        uiState = uiState.copy(
            firstName = uiState.firstName.trim(),
            lastName = uiState.lastName.trim(),
            identityDocumentIssuedIn = uiState.identityDocumentIssuedIn.trim(),
        )

        if (!validateFields()) return

        val phoneNumber = uiState.phoneNumber.toLong()
        val documentNumber = uiState.identityDocumentNumber.toLong()

        val newUser = CreateUser(
            firstName = uiState.firstName,
            lastName = uiState.lastName,
            phoneNumber = phoneNumber,
            identityDocumentType = uiState.identityDocumentType,
            identityDocumentNumber = documentNumber,
            identityDocumentIssuedIn = uiState.identityDocumentIssuedIn,
            availability = uiState.availability,
            role = uiState.role,
        )

        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage = null)
            val result = usersRepository.createUser(newUser)
            uiState = if (result.isSuccess) {
                AddUserUiState(isUserAdded = true)
            } else {
                uiState.copy(
                    isLoading = false,
                    errorMessage = result.exceptionOrNull()?.message ?: "An unexpected error occurred",
                )
            }
        }
    }

    private fun validateFields(): Boolean {
        val isValidFirstName = uiState.firstName.isNotBlank()
        val isValidLastName = uiState.lastName.isNotBlank()
        val isValidPhoneNumber = uiState.phoneNumber.isNotBlank() && uiState.phoneNumber.toLongOrNull() != null
        val isValidIdentityDocumentNumber = uiState.identityDocumentNumber.isNotBlank() && uiState.identityDocumentNumber.toLongOrNull() != null
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

data class AddUserUiState(
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
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isUserAdded: Boolean = false,
)
