package com.juanpablo0612.agrostaff.ui.users.list.components

import agrostaff.composeapp.generated.resources.Res
import agrostaff.composeapp.generated.resources.user_identity_document
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.juanpablo0612.agrostaff.data.users.model.IdentityDocumentType
import com.juanpablo0612.agrostaff.ui.theme.AgroStaffTheme
import com.juanpablo0612.agrostaff.ui.users.list.UserListItem
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun UserListItemCard(userListItem: UserListItem, onClick: () -> Unit, modifier: Modifier = Modifier) {
    OutlinedCard(modifier = modifier, onClick = onClick) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = userListItem.name,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(Res.string.user_identity_document, userListItem.identityDocumentType.toString(), userListItem.identityDocumentNumber),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview
@Composable
fun UserListItemCardPreview() {
    AgroStaffTheme {
        val userListItem = UserListItem(
            id = 1,
            name = "John Doe",
            identityDocumentType = IdentityDocumentType.CC,
            identityDocumentNumber = 12345678
        )

        UserListItemCard(userListItem = userListItem, onClick = {})
    }
}