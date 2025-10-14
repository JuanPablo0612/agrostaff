package com.juanpablo0612.agrostaff.ui.users.list.components

import agrostaff.composeapp.generated.resources.Res
import agrostaff.composeapp.generated.resources.user_list_add_content_description
import agrostaff.composeapp.generated.resources.users_title
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
fun UserListTopAppBar(onAddUser: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(Res.string.users_title)
            )
        },
        actions = {
            IconButton(onClick = onAddUser) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(Res.string.user_list_add_content_description)
                )
            }
        }
    )
}
