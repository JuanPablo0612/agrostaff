package com.juanpablo0612.agrostaff.ui.beds.list.components

import agrostaff.composeapp.generated.resources.Res
import agrostaff.composeapp.generated.resources.bed_list_add_content_description
import agrostaff.composeapp.generated.resources.beds_title
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BedListTopAppBar(onAddBed: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = stringResource(Res.string.beds_title))
        },
        actions = {
            IconButton(onClick = onAddBed) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(Res.string.bed_list_add_content_description)
                )
            }
        }
    )
}
