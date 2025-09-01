package com.juanpablo0612.agrostaff.ui.auth.sign_in

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanpablo0612.agrostaff.utils.validateEmail
import com.juanpablo0612.agrostaff.utils.validatePassword
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {
    var uiState by mutableStateOf(SignInUiState())
        private set

    fun onEmailChange(newEmail: String) {
        uiState = uiState.copy(email = newEmail.trim(), isValidEmail = true)
    }

    fun onPasswordChange(newPassword: String) {
        uiState = uiState.copy(password = newPassword, isValidPassword = true)
    }

    fun onPasswordVisibilityChange() {
        uiState = uiState.copy(showPassword = !uiState.showPassword)
    }

    private fun validateFields() {
        val isValidEmail = validateEmail(uiState.email)
        val isValidPassword = validatePassword(uiState.password)

        uiState = uiState.copy(
            isValidEmail = isValidEmail,
            isValidPassword = isValidPassword
        )
    }

    fun onSignIn() {
        viewModelScope.launch {
            validateFields()
            val allFieldsValid = uiState.isValidEmail && uiState.isValidPassword
            if (!allFieldsValid) return@launch
            uiState = uiState.copy(isLoading = true)
            delay(2000)
            uiState = uiState.copy(isLoading = false, isSuccess = true)
        }
    }
}

data class SignInUiState(
    val email: String = "",
    val isValidEmail: Boolean = true,
    val password: String = "",
    val isValidPassword: Boolean = true,
    val showPassword: Boolean = false,
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val exception: Exception? = null
)