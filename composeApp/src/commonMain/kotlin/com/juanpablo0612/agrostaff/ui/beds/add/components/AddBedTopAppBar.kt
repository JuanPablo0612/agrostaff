package com.juanpablo0612.agrostaff.ui.beds.add.components

import agrostaff.composeapp.generated.resources.Res
import agrostaff.composeapp.generated.resources.add_bed_title
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBedTopAppBar(onNavigateBack: () -> Unit, enableBack: Boolean) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = stringResource(Res.string.add_bed_title))
        },
        navigationIcon = {
            IconButton(
                onClick = onNavigateBack,
                enabled = enableBack,
            ) {
                Icon(imageVector = Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = null)
            }
        }
    )
}
