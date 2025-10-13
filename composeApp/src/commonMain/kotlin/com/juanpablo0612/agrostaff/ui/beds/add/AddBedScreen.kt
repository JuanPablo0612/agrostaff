package com.juanpablo0612.agrostaff.ui.beds.add

import agrostaff.composeapp.generated.resources.Res
import agrostaff.composeapp.generated.resources.add_bed_button_text
import agrostaff.composeapp.generated.resources.bed_block_label_text
import agrostaff.composeapp.generated.resources.bed_block_placeholder_text
import agrostaff.composeapp.generated.resources.bed_block_required_error_text
import agrostaff.composeapp.generated.resources.bed_block_retry_button_text
import agrostaff.composeapp.generated.resources.bed_description_error_text
import agrostaff.composeapp.generated.resources.bed_description_label_text
import agrostaff.composeapp.generated.resources.bed_loading_blocks_error_text
import agrostaff.composeapp.generated.resources.bed_loading_blocks_text
import agrostaff.composeapp.generated.resources.bed_name_error_text
import agrostaff.composeapp.generated.resources.bed_name_label_text
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.juanpablo0612.agrostaff.ui.beds.add.components.AddBedTopAppBar
import com.juanpablo0612.agrostaff.ui.components.BaseTextField
import com.juanpablo0612.agrostaff.ui.theme.AgroStaffTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AddBedScreen(
    onNavigateBack: () -> Unit,
    onBedAdded: () -> Unit,
    viewModel: AddBedViewModel = koinViewModel(),
) {
    val uiState = viewModel.uiState

    LaunchedEffect(uiState.isBedAdded) {
        if (uiState.isBedAdded) {
            onBedAdded()
        }
    }

    AddBedScreenContent(
        uiState = uiState,
        onBlockSelected = viewModel::onBlockSelected,
        onNameChange = viewModel::onNameChange,
        onDescriptionChange = viewModel::onDescriptionChange,
        onSave = viewModel::onSave,
        onNavigateBack = onNavigateBack,
        onRetryLoadBlocks = viewModel::retryLoadBlocks,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddBedScreenContent(
    uiState: AddBedUiState,
    onBlockSelected: (Int) -> Unit,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onSave: () -> Unit,
    onNavigateBack: () -> Unit,
    onRetryLoadBlocks: () -> Unit,
) {
    Scaffold(
        topBar = { AddBedTopAppBar(onNavigateBack = onNavigateBack, enableBack = true) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.TopCenter,
        ) {
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

                BlockSelector(
                    uiState = uiState,
                    onBlockSelected = onBlockSelected,
                    onRetryLoadBlocks = onRetryLoadBlocks,
                )

                Spacer(modifier = Modifier.height(12.dp))

                val nameSupportingText = if (!uiState.isValidName) {
                    @Composable {
                        Text(text = stringResource(Res.string.bed_name_error_text))
                    }
                } else {
                    null
                }

                BaseTextField(
                    value = uiState.name,
                    onValueChange = onNameChange,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !uiState.isLoading,
                    label = { Text(stringResource(Res.string.bed_name_label_text)) },
                    supportingText = nameSupportingText,
                    isError = !uiState.isValidName,
                    singleLine = true,
                )

                Spacer(modifier = Modifier.height(12.dp))

                val descriptionSupportingText = if (!uiState.isValidDescription) {
                    @Composable {
                        Text(text = stringResource(Res.string.bed_description_error_text))
                    }
                } else {
                    null
                }

                BaseTextField(
                    value = uiState.description,
                    onValueChange = onDescriptionChange,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !uiState.isLoading,
                    label = { Text(stringResource(Res.string.bed_description_label_text)) },
                    supportingText = descriptionSupportingText,
                    isError = !uiState.isValidDescription,
                    minLines = 4,
                )

                uiState.errorMessage?.let { message ->
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = message,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = onSave,
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
                        Text(stringResource(Res.string.add_bed_button_text))
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BlockSelector(
    uiState: AddBedUiState,
    onBlockSelected: (Int) -> Unit,
    onRetryLoadBlocks: () -> Unit,
) {
    val blockOptions = uiState.blocks
    val selectedBlock = blockOptions.firstOrNull { it.id == uiState.selectedBlockId }
    val expandedState = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
    ) {
        val supportingText = when {
            !uiState.isValidBlock -> {
                @Composable { Text(text = stringResource(Res.string.bed_block_required_error_text)) }
            }

            uiState.blocksErrorMessage != null -> {
                @Composable {
                    Column {
                        Text(
                            text = uiState.blocksErrorMessage.takeUnless { it.isNullOrBlank() }
                                ?: stringResource(Res.string.bed_loading_blocks_error_text),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error,
                        )
                        TextButton(
                            onClick = onRetryLoadBlocks,
                            enabled = !uiState.isLoadingBlocks
                        ) {
                            Text(text = stringResource(Res.string.bed_block_retry_button_text))
                        }
                    }
                }
            }

            else -> null
        }

        ExposedDropdownMenuBox(
            expanded = expandedState.value,
            onExpandedChange = { expandedState.value = !expandedState.value }
        ) {
            BaseTextField(
                value = selectedBlock?.name ?: "",
                onValueChange = {},
                modifier = Modifier
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                    .fillMaxWidth(),
                readOnly = true,
                enabled = !uiState.isLoading,
                label = { Text(stringResource(Res.string.bed_block_label_text)) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedState.value)
                },
                supportingText = supportingText,
                isError = !uiState.isValidBlock || uiState.blocksErrorMessage != null,
                placeholder = {
                    Text(text = stringResource(Res.string.bed_block_placeholder_text))
                },
            )

            ExposedDropdownMenu(
                expanded = expandedState.value,
                onDismissRequest = { expandedState.value = false }
            ) {
                blockOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option.name) },
                        onClick = {
                            expandedState.value = false
                            onBlockSelected(option.id)
                        }
                    )
                }
            }
        }

        if (uiState.isLoadingBlocks) {
            Spacer(modifier = Modifier.height(8.dp))
            CircularProgressIndicator(
                modifier = Modifier.size(16.dp),
                strokeWidth = 2.dp,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(Res.string.bed_loading_blocks_text),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Preview
@Composable
private fun AddBedScreenContentPreview() {
    AgroStaffTheme {
        AddBedScreenContent(
            uiState = AddBedUiState(
                blocks = listOf(
                    BlockOption(id = 1, name = "Alpha"),
                    BlockOption(id = 2, name = "Beta"),
                )
            ),
            onBlockSelected = {},
            onNameChange = {},
            onDescriptionChange = {},
            onSave = {},
            onNavigateBack = {},
            onRetryLoadBlocks = {},
        )
    }
}
