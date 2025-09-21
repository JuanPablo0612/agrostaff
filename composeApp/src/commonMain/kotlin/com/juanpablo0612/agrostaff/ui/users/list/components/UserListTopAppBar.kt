package com.juanpablo0612.agrostaff.ui.users.list.components

import agrostaff.composeapp.generated.resources.Res
import agrostaff.composeapp.generated.resources.users_title
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListTopAppBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(Res.string.users_title)
            )
        }
    )
}