package com.juanpablo0612.agrostaff.ui.blocks.list

import agrostaff.composeapp.generated.resources.Res
import agrostaff.composeapp.generated.resources.block_list_delete_error
import agrostaff.composeapp.generated.resources.block_list_empty_state
import agrostaff.composeapp.generated.resources.block_list_generic_error
import agrostaff.composeapp.generated.resources.block_list_retry_button
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.juanpablo0612.agrostaff.ui.blocks.list.components.BlockListItemCard
import com.juanpablo0612.agrostaff.ui.blocks.list.components.BlockListTopAppBar
import com.juanpablo0612.agrostaff.ui.theme.AgroStaffTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BlockListScreen(
    onNavigateToAddBlock: () -> Unit,
    onNavigateToBlockDetail: (blockId: Int) -> Unit,
    viewModel: BlockListViewModel = koinViewModel(),
) {
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }

    val errorMessage = uiState.error?.let { error ->
        when (error) {
            is BlockListError.LoadFailed -> error.message ?: stringResource(Res.string.block_list_generic_error)
            is BlockListError.DeleteFailed -> error.message ?: stringResource(Res.string.block_list_delete_error)
        }
    }

    LaunchedEffect(errorMessage) {
        if (errorMessage != null) {
            snackbarHostState.showSnackbar(errorMessage)
            viewModel.consumeError()
        }
    }

    BlockListScreenContent(
        uiState = uiState,
        onBlockClick = onNavigateToBlockDetail,
        onAddBlock = onNavigateToAddBlock,
        onDeleteBlock = viewModel::deleteBlock,
        onRetry = viewModel::retry,
        snackbarHostState = snackbarHostState,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BlockListScreenContent(
    uiState: BlockListUiState,
    onBlockClick: (blockId: Int) -> Unit,
    onAddBlock: () -> Unit,
    onDeleteBlock: (Int) -> Unit,
    onRetry: () -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    Scaffold(
        topBar = {
            BlockListTopAppBar(onAddBlock = onAddBlock)
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { snackbarData ->
                Snackbar(snackbarData)
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                uiState.isLoading && uiState.blocks.isEmpty() -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                uiState.blocks.isEmpty() -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 32.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(text = stringResource(Res.string.block_list_empty_state))
                        if (uiState.error is BlockListError.LoadFailed) {
                            Spacer(modifier = Modifier.height(16.dp))
                            FilledTonalButton(onClick = onRetry) {
                                Text(text = stringResource(Res.string.block_list_retry_button))
                            }
                        }
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = uiState.blocks,
                            key = { block -> block.id }
                        ) { block ->
                            val isDeleting = uiState.deletingBlockIds.contains(block.id)
                            BlockListItemCard(
                                block = block,
                                onClick = { onBlockClick(block.id) },
                                onDelete = { onDeleteBlock(block.id) },
                                isDeleting = isDeleting,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun BlockListScreenContentPreview() {
    AgroStaffTheme {
        BlockListScreenContent(
            uiState = BlockListUiState(
                blocks = List(5) {
                    BlockListItem(
                        id = it,
                        name = "Block $it",
                        description = "Description for block $it"
                    )
                }
            ),
            onBlockClick = {},
            onAddBlock = {},
            onDeleteBlock = {},
            onRetry = {},
            snackbarHostState = SnackbarHostState(),
        )
    }
}
