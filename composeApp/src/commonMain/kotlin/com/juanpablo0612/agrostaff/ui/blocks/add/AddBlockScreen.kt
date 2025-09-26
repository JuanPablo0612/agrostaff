package com.juanpablo0612.agrostaff.ui.blocks.add

import agrostaff.composeapp.generated.resources.Res
import agrostaff.composeapp.generated.resources.add_block_button_text
import agrostaff.composeapp.generated.resources.block_description_error_text
import agrostaff.composeapp.generated.resources.block_description_label_text
import agrostaff.composeapp.generated.resources.block_name_error_text
import agrostaff.composeapp.generated.resources.block_name_label_text
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.juanpablo0612.agrostaff.ui.blocks.add.components.AddBlockTopAppBar
import com.juanpablo0612.agrostaff.ui.components.BaseTextField
import com.juanpablo0612.agrostaff.ui.theme.AgroStaffTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AddBlockScreen(
    onNavigateBack: () -> Unit,
    onBlockAdded: () -> Unit,
    viewModel: AddBlockViewModel = koinViewModel(),
) {
    val uiState = viewModel.uiState

    LaunchedEffect(uiState.isBlockAdded) {
        if (uiState.isBlockAdded) {
            onBlockAdded()
        }
    }

    AddBlockScreenContent(
        uiState = uiState,
        onNameChange = viewModel::onNameChange,
        onDescriptionChange = viewModel::onDescriptionChange,
        onSave = viewModel::onSave,
        onNavigateBack = onNavigateBack,
    )
}

@Composable
internal fun AddBlockScreenContent(
    uiState: AddBlockUiState,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onSave: () -> Unit,
    onNavigateBack: () -> Unit,
) {
    Scaffold(
        topBar = { AddBlockTopAppBar(onNavigateBack = onNavigateBack, enableBack = true) }
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

                val nameSupportingText = if (!uiState.isValidName) {
                    @Composable {
                        Text(text = stringResource(Res.string.block_name_error_text))
                    }
                } else {
                    null
                }

                BaseTextField(
                    value = uiState.name,
                    onValueChange = onNameChange,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !uiState.isLoading,
                    label = { Text(stringResource(Res.string.block_name_label_text)) },
                    supportingText = nameSupportingText,
                    isError = !uiState.isValidName,
                    singleLine = true,
                )

                Spacer(modifier = Modifier.height(12.dp))

                val descriptionSupportingText = if (!uiState.isValidDescription) {
                    @Composable {
                        Text(text = stringResource(Res.string.block_description_error_text))
                    }
                } else {
                    null
                }

                BaseTextField(
                    value = uiState.description,
                    onValueChange = onDescriptionChange,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !uiState.isLoading,
                    label = { Text(stringResource(Res.string.block_description_label_text)) },
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
                        Text(stringResource(Res.string.add_block_button_text))
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Preview
@Composable
private fun AddBlockScreenContentPreview() {
    AgroStaffTheme {
        AddBlockScreenContent(
            uiState = AddBlockUiState(),
            onNameChange = {},
            onDescriptionChange = {},
            onSave = {},
            onNavigateBack = {},
        )
    }
}

@Preview
@Composable
private fun AddBlockScreenContentErrorPreview() {
    AgroStaffTheme {
        AddBlockScreenContent(
            uiState = AddBlockUiState(
                name = "",
                isValidName = false,
                description = "",
                isValidDescription = false,
                errorMessage = "Unable to add block",
            ),
            onNameChange = {},
            onDescriptionChange = {},
            onSave = {},
            onNavigateBack = {},
        )
    }
}
