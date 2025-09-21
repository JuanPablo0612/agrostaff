package com.juanpablo0612.agrostaff.ui.blocks.add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.juanpablo0612.agrostaff.ui.blocks.add.components.AddBlockTopAppBar
import com.juanpablo0612.agrostaff.ui.components.BaseTextField
import com.juanpablo0612.agrostaff.ui.theme.AgroStaffTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
private fun AddBlockScreenContent(
    uiState: AddBlockUiState,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = { AddBlockTopAppBar(onNavigateBack = onNavigateBack, enableBack = true) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .imePadding()
                .verticalScroll(rememberScrollState())
        ) {
            BaseTextField(
                value = uiState.name,
                onValueChange = onNameChange,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(12.dp))
            BaseTextField(
                value = uiState.description,
                onValueChange = onDescriptionChange,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview
@Composable
fun AddBlockScreenPreview() {
    AgroStaffTheme {
        val uiState = AddBlockUiState()
        AddBlockScreenContent(uiState = uiState, onNameChange = {}, onDescriptionChange = {}, onNavigateBack = {})
    }
}