package com.juanpablo0612.agrostaff.ui.auth.sign_in

import agrostaff.composeapp.generated.resources.Res
import agrostaff.composeapp.generated.resources.login_button_text
import agrostaff.composeapp.generated.resources.login_create_account_suffix
import agrostaff.composeapp.generated.resources.login_forgot_password_button_text
import agrostaff.composeapp.generated.resources.login_log_in_to_continue
import agrostaff.composeapp.generated.resources.login_no_account_prefix
import agrostaff.composeapp.generated.resources.login_welcome_back
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.juanpablo0612.agrostaff.ui.auth.components.EmailTextField
import com.juanpablo0612.agrostaff.ui.auth.components.PasswordTextField
import com.juanpablo0612.agrostaff.ui.theme.AgroStaffTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = koinViewModel(),
    onNavigateToPasswordRecovery: () -> Unit,
    onNavigateToSignUp: () -> Unit
) {
    val uiState = viewModel.uiState

    SignInScreenContent(
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onPasswordVisibilityChange = viewModel::onPasswordVisibilityChange,
        onNavigateToPasswordRecovery = onNavigateToPasswordRecovery,
        onNavigateToRegister = onNavigateToSignUp,
        onSignIn = viewModel::onSignIn
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SignInScreenContent(
    uiState: SignInUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordVisibilityChange: () -> Unit,
    onNavigateToPasswordRecovery: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onSignIn: () -> Unit
) {
    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .widthIn(max = 560.dp)
                    .imePadding()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(2f))

                Text(
                    text = stringResource(Res.string.login_welcome_back),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(Res.string.login_log_in_to_continue),
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(24.dp))

                EmailTextField(
                    value = uiState.email,
                    onValueChange = onEmailChange,
                    enabled = !uiState.isLoading,
                    isError = !uiState.isValidEmail,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                PasswordTextField(
                    value = uiState.password,
                    onValueChange = onPasswordChange,
                    enabled = !uiState.isLoading,
                    isError = !uiState.isValidPassword,
                    showPassword = uiState.showPassword,
                    onPasswordVisibilityChange = onPasswordVisibilityChange,
                    modifier = Modifier.fillMaxWidth(),
                    imeAction = ImeAction.Done
                )

                TextButton(
                    onClick = onNavigateToPasswordRecovery,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(
                        text = stringResource(Res.string.login_forgot_password_button_text)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = onSignIn,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !uiState.isLoading
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator()
                    } else {
                        Text(stringResource(Res.string.login_button_text))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = buildAnnotatedString {
                        append(stringResource(Res.string.login_no_account_prefix))

                        append(" ")

                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                            append(stringResource(Res.string.login_create_account_suffix))
                        }
                    },
                    modifier = Modifier.clickable(onClick = onNavigateToRegister)
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Preview
@Composable
fun SignInScreenContentPreview() {
    AgroStaffTheme {
        val uiState = SignInUiState(
            email = "test@example.com",
            password = "password",
            showPassword = false,
            isLoading = false
        )
        SignInScreenContent(
            uiState = uiState,
            onEmailChange = {},
            onPasswordChange = {},
            onPasswordVisibilityChange = {},
            onNavigateToPasswordRecovery = {},
            onNavigateToRegister = {},
            onSignIn = {}
        )
    }
}