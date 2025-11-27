package com.juanpablo0612.agrostaff.ui.users.detail

import agrostaff.composeapp.generated.resources.Res
import agrostaff.composeapp.generated.resources.add_user_button_text
import agrostaff.composeapp.generated.resources.availability_absent
import agrostaff.composeapp.generated.resources.availability_active
import agrostaff.composeapp.generated.resources.availability_rest
import agrostaff.composeapp.generated.resources.contact_number_error_text
import agrostaff.composeapp.generated.resources.contact_number_label_text
import agrostaff.composeapp.generated.resources.first_name_error_text
import agrostaff.composeapp.generated.resources.first_name_label_text
import agrostaff.composeapp.generated.resources.identity_document_issued_in_error_text
import agrostaff.composeapp.generated.resources.identity_document_issued_in_label_text
import agrostaff.composeapp.generated.resources.identity_document_number_error_text
import agrostaff.composeapp.generated.resources.identity_document_number_label_text
import agrostaff.composeapp.generated.resources.identity_document_type_label_text
import agrostaff.composeapp.generated.resources.last_name_error_text
import agrostaff.composeapp.generated.resources.last_name_label_text
import agrostaff.composeapp.generated.resources.role_admin
import agrostaff.composeapp.generated.resources.role_supervisor
import agrostaff.composeapp.generated.resources.role_worker
import agrostaff.composeapp.generated.resources.user_availability_label_text
import agrostaff.composeapp.generated.resources.user_role_label_text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.juanpablo0612.agrostaff.data.users.model.AvailabilityStatus
import com.juanpablo0612.agrostaff.data.users.model.IdentityDocumentType
import com.juanpablo0612.agrostaff.data.users.model.UserRole
import com.juanpablo0612.agrostaff.ui.components.BaseTextField
import com.juanpablo0612.agrostaff.ui.users.detail.components.UserDetailTopAppBar
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UserDetailScreen(
    onNavigateBack: () -> Unit,
    onUserUpdated: () -> Unit,
    viewModel: UserDetailViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState

    LaunchedEffect(uiState.isUserUpdated) {
        if (uiState.isUserUpdated) {
            onUserUpdated()
        }
    }

    UserDetailScreenContent(
        uiState = uiState,
        onFirstNameChange = viewModel::onFirstNameChange,
        onLastNameChange = viewModel::onLastNameChange,
        onPhoneNumberChange = viewModel::onPhoneNumberChange,
        onIdentityDocumentTypeChange = viewModel::onIdentityDocumentTypeChange,
        onIdentityDocumentNumberChange = viewModel::onIdentityDocumentNumberChange,
        onIdentityDocumentIssuedInChange = viewModel::onIdentityDocumentIssuedInChange,
        onAvailabilityChange = viewModel::onAvailabilityChange,
        onRoleChange = viewModel::onRoleChange,
        onUpdate = viewModel::onUpdate,
        onEditClick = viewModel::onEditingChange,
        onNavigateBack = onNavigateBack
    )
}

@Composable
internal fun UserDetailScreenContent(
    uiState: UserDetailUiState,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onIdentityDocumentTypeChange: (IdentityDocumentType) -> Unit,
    onIdentityDocumentNumberChange: (String) -> Unit,
    onIdentityDocumentIssuedInChange: (String) -> Unit,
    onAvailabilityChange: (AvailabilityStatus) -> Unit,
    onRoleChange: (UserRole) -> Unit,
    onUpdate: () -> Unit,
    onEditClick: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val roleLabels = mapOf(
        UserRole.ADMIN to stringResource(Res.string.role_admin),
        UserRole.SUPERVISOR to stringResource(Res.string.role_supervisor),
        UserRole.WORKER to stringResource(Res.string.role_worker),
    )
    val availabilityLabels = mapOf(
        AvailabilityStatus.ACTIVE to stringResource(Res.string.availability_active),
        AvailabilityStatus.REST to stringResource(Res.string.availability_rest),
        AvailabilityStatus.ABSENT to stringResource(Res.string.availability_absent),
    )

    Scaffold(
        topBar = {
            UserDetailTopAppBar(
                onEditClick = onEditClick,
                onNavigateBack = onNavigateBack,
                enableBack = true
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                uiState.error != null -> {

                }

                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                            .widthIn(max = 560.dp)
                            .imePadding()
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Spacer(modifier = Modifier.height(24.dp))

                        val firstNameSupportingText = if (!uiState.isValidFirstName) {
                            @Composable {
                                Text(text = stringResource(Res.string.first_name_error_text))
                            }
                        } else {
                            null
                        }

                        BaseTextField(
                            value = uiState.firstName,
                            onValueChange = onFirstNameChange,
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !uiState.isLoading,
                            readOnly = !uiState.isEditing,
                            label = { Text(text = stringResource(Res.string.first_name_label_text)) },
                            supportingText = firstNameSupportingText,
                            isError = !uiState.isValidFirstName,
                            singleLine = true,
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        val lastNameSupportingText = if (!uiState.isValidLastName) {
                            @Composable {
                                Text(text = stringResource(Res.string.last_name_error_text))
                            }
                        } else {
                            null
                        }

                        BaseTextField(
                            value = uiState.lastName,
                            onValueChange = onLastNameChange,
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !uiState.isLoading,
                            readOnly = !uiState.isEditing,
                            label = { Text(text = stringResource(Res.string.last_name_label_text)) },
                            supportingText = lastNameSupportingText,
                            isError = !uiState.isValidLastName,
                            singleLine = true,
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        DropdownField(
                            label = stringResource(Res.string.identity_document_type_label_text),
                            value = uiState.identityDocumentType.name,
                            enabled = !uiState.isLoading,
                            readOnly = !uiState.isEditing,
                            options = IdentityDocumentType.entries,
                            optionLabel = { it.name },
                            onOptionSelected = onIdentityDocumentTypeChange,
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        val documentNumberSupportingText =
                            if (!uiState.isValidIdentityDocumentNumber) {
                                @Composable {
                                    Text(text = stringResource(Res.string.identity_document_number_error_text))
                                }
                            } else {
                                null
                            }

                        BaseTextField(
                            value = uiState.identityDocumentNumber,
                            onValueChange = onIdentityDocumentNumberChange,
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !uiState.isLoading,
                            readOnly = !uiState.isEditing,
                            label = { Text(text = stringResource(Res.string.identity_document_number_label_text)) },
                            supportingText = documentNumberSupportingText,
                            isError = !uiState.isValidIdentityDocumentNumber,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        val documentIssuedInSupportingText =
                            if (!uiState.isValidIdentityDocumentIssuedIn) {
                                @Composable {
                                    Text(text = stringResource(Res.string.identity_document_issued_in_error_text))
                                }
                            } else {
                                null
                            }

                        BaseTextField(
                            value = uiState.identityDocumentIssuedIn,
                            onValueChange = onIdentityDocumentIssuedInChange,
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !uiState.isLoading,
                            readOnly = !uiState.isEditing,
                            label = { Text(text = stringResource(Res.string.identity_document_issued_in_label_text)) },
                            supportingText = documentIssuedInSupportingText,
                            isError = !uiState.isValidIdentityDocumentIssuedIn,
                            singleLine = true,
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        val phoneSupportingText = if (!uiState.isValidPhoneNumber) {
                            @Composable {
                                Text(text = stringResource(Res.string.contact_number_error_text))
                            }
                        } else {
                            null
                        }

                        BaseTextField(
                            value = uiState.phoneNumber,
                            onValueChange = onPhoneNumberChange,
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !uiState.isLoading,
                            readOnly = !uiState.isEditing,
                            label = { Text(text = stringResource(Res.string.contact_number_label_text)) },
                            supportingText = phoneSupportingText,
                            isError = !uiState.isValidPhoneNumber,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        DropdownField(
                            label = stringResource(Res.string.user_role_label_text),
                            value = roleLabels.getValue(uiState.role),
                            enabled = !uiState.isLoading,
                            readOnly = !uiState.isEditing,
                            options = UserRole.entries,
                            optionLabel = { role -> roleLabels.getValue(role) },
                            onOptionSelected = onRoleChange,
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        DropdownField(
                            label = stringResource(Res.string.user_availability_label_text),
                            value = availabilityLabels.getValue(uiState.availability),
                            enabled = !uiState.isLoading,
                            readOnly = !uiState.isEditing,
                            options = AvailabilityStatus.entries,
                            optionLabel = { availability -> availabilityLabels.getValue(availability) },
                            onOptionSelected = onAvailabilityChange,
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Button(
                            onClick = onUpdate,
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !uiState.isLoading,
                        ) {
                            if (uiState.isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    strokeWidth = 2.dp,
                                )
                            } else {
                                Text(text = stringResource(Res.string.add_user_button_text))
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun <T> DropdownField(
    label: String,
    value: String,
    enabled: Boolean,
    readOnly: Boolean,
    options: List<T>,
    optionLabel: (T) -> String,
    onOptionSelected: (T) -> Unit,
) {
    val expandedState = rememberSaveable { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expandedState.value,
        onExpandedChange = {
            if (enabled && !readOnly) {
                expandedState.value = !expandedState.value
            }
        }
    ) {
        BaseTextField(
            value = value,
            onValueChange = {},
            modifier = Modifier
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth(),
            enabled = enabled,
            readOnly = readOnly,
            label = { Text(text = label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedState.value) },
        )

        ExposedDropdownMenu(
            expanded = expandedState.value,
            onDismissRequest = { expandedState.value = false },
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = optionLabel(option)) },
                    onClick = {
                        onOptionSelected(option)
                        expandedState.value = false
                    },
                    enabled = enabled,
                )
            }
        }
    }
}