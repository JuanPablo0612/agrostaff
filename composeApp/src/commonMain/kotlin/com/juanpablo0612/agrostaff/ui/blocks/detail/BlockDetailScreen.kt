package com.juanpablo0612.agrostaff.ui.blocks.detail

import agrostaff.composeapp.generated.resources.Res
import agrostaff.composeapp.generated.resources.block_description_error_text
import agrostaff.composeapp.generated.resources.block_description_label_text
import agrostaff.composeapp.generated.resources.block_name_error_text
import agrostaff.composeapp.generated.resources.block_name_label_text
import agrostaff.composeapp.generated.resources.update_block_button_text
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
import com.juanpablo0612.agrostaff.ui.blocks.detail.components.BlockDetailTopAppBar
import com.juanpablo0612.agrostaff.ui.components.BaseTextField
import com.juanpablo0612.agrostaff.ui.theme.AgroStaffTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BlockDetailScreen(
    onNavigateBack: () -> Unit,
    onBlockUpdated: () -> Unit,
    viewModel: BlockDetailViewModel = koinViewModel(),
) {
    val uiState = viewModel.uiState

    LaunchedEffect(uiState.isBlockUpdated) {
        if (uiState.isBlockUpdated) {
            onBlockUpdated()
        }
    }

    BlockDetailScreenContent(
        uiState = uiState,
        onNameChange = viewModel::onNameChange,
        onDescriptionChange = viewModel::onDescriptionChange,
        onUpdate = viewModel::onUpdate,
        onEditClick = viewModel::onEditingChange,
        onNavigateBack = onNavigateBack
    )
}

@Composable
internal fun BlockDetailScreenContent(
    uiState: BlockDetailUiState,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onUpdate: () -> Unit,
    onEditClick: () -> Unit,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            BlockDetailTopAppBar(
                onEditClick = onEditClick,
                onNavigateBack = onNavigateBack,
                enableBack = !uiState.isLoading
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                uiState.isLoading && uiState.blockId == null -> {
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
                            readOnly = !uiState.isEditing,
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
                            readOnly = !uiState.isEditing,
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !uiState.isLoading,
                            label = { Text(stringResource(Res.string.block_description_label_text)) },
                            supportingText = descriptionSupportingText,
                            isError = !uiState.isValidDescription,
                            minLines = 4,
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
                                Text(stringResource(Res.string.update_block_button_text))
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun BlockDetailScreenPreview() {
    AgroStaffTheme {
        BlockDetailScreenContent(
            uiState = BlockDetailUiState(
                isLoading = false,
                name = "",
                description = "",
                error = null,
            ),
            onNameChange = {},
            onDescriptionChange = {},
            onUpdate = {},
            onEditClick = {},
            onNavigateBack = {}
        )
    }
}