package com.juanpablo0612.agrostaff.ui.beds.add

import agrostaff.composeapp.generated.resources.Res
import agrostaff.composeapp.generated.resources.add_bed_button_text
import agrostaff.composeapp.generated.resources.bed_description_error_text
import agrostaff.composeapp.generated.resources.bed_description_label_text
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.juanpablo0612.agrostaff.ui.beds.add.components.AddBedTopAppBar
import com.juanpablo0612.agrostaff.ui.beds.components.BlockOption
import com.juanpablo0612.agrostaff.ui.beds.components.BlockSelector
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
                    blockOptions = uiState.blocks,
                    selectedBlock = uiState.blocks.firstOrNull { it.id == uiState.selectedBlockId },
                    isValidBlock = uiState.isValidBlock,
                    isLoading = uiState.isLoading,
                    isLoadingBlocks = uiState.isLoadingBlocks,
                    blocksErrorMessage = uiState.blocksErrorMessage,
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
