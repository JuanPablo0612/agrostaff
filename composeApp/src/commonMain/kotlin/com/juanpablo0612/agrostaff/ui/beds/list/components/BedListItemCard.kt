package com.juanpablo0612.agrostaff.ui.beds.list.components

import agrostaff.composeapp.generated.resources.Res
import agrostaff.composeapp.generated.resources.bed_list_block_label
import agrostaff.composeapp.generated.resources.bed_list_delete_content_description
import androidx.compose.foundation.layout.Arrangement
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
import com.juanpablo0612.agrostaff.ui.beds.list.BedListItem
import com.juanpablo0612.agrostaff.ui.theme.AgroStaffTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BedListItemCard(
    bed: BedListItem,
    onDelete: () -> Unit,
    isDeleting: Boolean,
    modifier: Modifier = Modifier,
) {
    OutlinedCard(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = bed.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = bed.description,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(Res.string.bed_list_block_label, bed.blockId),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

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
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(Res.string.bed_list_delete_content_description)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun BedListItemCardPreview() {
    AgroStaffTheme {
        BedListItemCard(
            bed = BedListItem(
                id = 1,
                name = "Bed A",
                description = "Lettuce hydroponic line",
                blockId = 3,
            ),
            onDelete = {},
            isDeleting = false,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun BedListItemCardDeletingPreview() {
    AgroStaffTheme {
        BedListItemCard(
            bed = BedListItem(
                id = 1,
                name = "Bed A",
                description = "Lettuce hydroponic line",
                blockId = 3,
            ),
            onDelete = {},
            isDeleting = true,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
