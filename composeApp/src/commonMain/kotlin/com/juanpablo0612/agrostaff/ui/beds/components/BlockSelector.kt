package com.juanpablo0612.agrostaff.ui.beds.components

import agrostaff.composeapp.generated.resources.Res
import agrostaff.composeapp.generated.resources.bed_block_label_text
import agrostaff.composeapp.generated.resources.bed_block_placeholder_text
import agrostaff.composeapp.generated.resources.bed_block_required_error_text
import agrostaff.composeapp.generated.resources.bed_block_retry_button_text
import agrostaff.composeapp.generated.resources.bed_loading_blocks_error_text
import agrostaff.composeapp.generated.resources.bed_loading_blocks_text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.juanpablo0612.agrostaff.ui.components.BaseTextField
import org.jetbrains.compose.resources.stringResource

data class BlockOption(
    val id: Int,
    val name: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlockSelector(
    blockOptions: List<BlockOption>,
    selectedBlock: BlockOption?,
    isValidBlock: Boolean,
    isLoading: Boolean,
    isLoadingBlocks: Boolean,
    blocksErrorMessage: String?,
    onBlockSelected: (Int) -> Unit,
    onRetryLoadBlocks: () -> Unit,
) {
    val expandedState = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
    ) {
        val supportingText = when {
            !isValidBlock -> {
                @Composable { Text(text = stringResource(Res.string.bed_block_required_error_text)) }
            }

            blocksErrorMessage != null -> {
                @Composable {
                    Column {
                        Text(
                            text = blocksErrorMessage.takeUnless { it.isBlank() }
                                ?: stringResource(Res.string.bed_loading_blocks_error_text),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error,
                        )
                        TextButton(
                            onClick = onRetryLoadBlocks,
                            enabled = !isLoadingBlocks
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
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                    .fillMaxWidth(),
                readOnly = true,
                enabled = !isLoading,
                label = { Text(stringResource(Res.string.bed_block_label_text)) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedState.value)
                },
                supportingText = supportingText,
                isError = !isValidBlock || blocksErrorMessage != null,
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

        if (isLoadingBlocks) {
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