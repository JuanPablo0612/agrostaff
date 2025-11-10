package com.juanpablo0612.agrostaff.ui.beds.detail.components

import agrostaff.composeapp.generated.resources.Res
import agrostaff.composeapp.generated.resources.bed_detail_title
import agrostaff.composeapp.generated.resources.block_detail_title
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BedDetailTopAppBar(onEditClick: () -> Unit, onNavigateBack: () -> Unit, enableBack: Boolean) {
    TopAppBar(
        title = {
            Text(text = stringResource(Res.string.bed_detail_title))
        },
        actions = {
            IconButton(onClick = onEditClick) {
                Icon(imageVector = Icons.Outlined.Edit, contentDescription = null)
            }
        },
        navigationIcon = {
            IconButton(
                onClick = onNavigateBack,
                enabled = enableBack
            ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
            }
        }
    )
}