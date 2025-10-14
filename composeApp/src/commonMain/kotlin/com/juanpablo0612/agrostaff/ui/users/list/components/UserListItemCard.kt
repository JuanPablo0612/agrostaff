package com.juanpablo0612.agrostaff.ui.users.list.components

import agrostaff.composeapp.generated.resources.Res
import agrostaff.composeapp.generated.resources.availability_absent
import agrostaff.composeapp.generated.resources.availability_active
import agrostaff.composeapp.generated.resources.availability_rest
import agrostaff.composeapp.generated.resources.role_admin
import agrostaff.composeapp.generated.resources.role_supervisor
import agrostaff.composeapp.generated.resources.role_worker
import agrostaff.composeapp.generated.resources.user_identity_document
import agrostaff.composeapp.generated.resources.user_list_availability_label
import agrostaff.composeapp.generated.resources.user_list_delete_content_description
import agrostaff.composeapp.generated.resources.user_list_phone_label
import agrostaff.composeapp.generated.resources.user_list_role_label
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.juanpablo0612.agrostaff.data.users.model.AvailabilityStatus
import com.juanpablo0612.agrostaff.data.users.model.IdentityDocumentType
import com.juanpablo0612.agrostaff.data.users.model.UserRole
import com.juanpablo0612.agrostaff.ui.theme.AgroStaffTheme
import com.juanpablo0612.agrostaff.ui.users.list.UserListItem
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun UserListItemCard(
    userListItem: UserListItem,
    onDelete: () -> Unit,
    isDeleting: Boolean,
    modifier: Modifier = Modifier,
) {
    OutlinedCard(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = userListItem.name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = onDelete,
                    enabled = !isDeleting,
                ) {
                    if (isDeleting) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = stringResource(Res.string.user_list_delete_content_description)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(
                    Res.string.user_identity_document,
                    userListItem.identityDocumentType.toString(),
                    userListItem.identityDocumentNumber,
                ),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(
                    Res.string.user_list_role_label,
                    userListItem.role.asDisplayName(),
                ),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(
                    Res.string.user_list_availability_label,
                    userListItem.availability.asDisplayName(),
                ),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(
                    Res.string.user_list_phone_label,
                    userListItem.phoneNumber,
                ),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun UserRole.asDisplayName(): String = when (this) {
    UserRole.ADMIN -> stringResource(Res.string.role_admin)
    UserRole.SUPERVISOR -> stringResource(Res.string.role_supervisor)
    UserRole.WORKER -> stringResource(Res.string.role_worker)
}

@Composable
private fun AvailabilityStatus.asDisplayName(): String = when (this) {
    AvailabilityStatus.ACTIVE -> stringResource(Res.string.availability_active)
    AvailabilityStatus.REST -> stringResource(Res.string.availability_rest)
    AvailabilityStatus.ABSENT -> stringResource(Res.string.availability_absent)
}

@Preview
@Composable
private fun UserListItemCardPreview() {
    AgroStaffTheme {
        UserListItemCard(
            userListItem = UserListItem(
                id = 1,
                name = "John Doe",
                identityDocumentType = IdentityDocumentType.CC,
                identityDocumentNumber = 123456789L,
                phoneNumber = 3201234567L,
                availability = AvailabilityStatus.ACTIVE,
                role = UserRole.SUPERVISOR,
            ),
            onDelete = {},
            isDeleting = false,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun UserListItemCardDeletingPreview() {
    AgroStaffTheme {
        UserListItemCard(
            userListItem = UserListItem(
                id = 1,
                name = "Jane Smith",
                identityDocumentType = IdentityDocumentType.CE,
                identityDocumentNumber = 987654321L,
                phoneNumber = 3107654321L,
                availability = AvailabilityStatus.REST,
                role = UserRole.WORKER,
            ),
            onDelete = {},
            isDeleting = true,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
