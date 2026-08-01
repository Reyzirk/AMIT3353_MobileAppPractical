package com.example.practical6.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.practical6.model.ShoppingItem
import com.example.practical6.ui.theme.Practical6Theme


@Composable
fun ShoppingItemRow (
    item: ShoppingItem,
    onCheckedChange: (Boolean) -> Unit,
    onDeleteClick: () -> Unit
) {
    Card (
        modifier = Modifier.fillMaxWidth()
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = item.purchased,
                onCheckedChange = onCheckedChange
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium,
                    textDecoration =
                        if (item.purchased) TextDecoration.LineThrough else null
                )

                Text(
                    text = buildString {
                        append(item.category)
                        append(" - ")
                        append(item.priority)
                        if (item.urgent) append(" - URGENT")
                    },
                    style = MaterialTheme.typography.bodySmall
                )
            }
            IconButton(
                onClick = onDeleteClick
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete Item"
                )
            }
        }
    }
}

@Preview (showBackground = true)
@Composable

fun ShoppingItemRowPreview() {
    Practical6Theme {
        ShoppingItemRow(
            item = ShoppingItem (1, "Milk", "Groceries", "High", urgent = true),
            onCheckedChange = {},
            onDeleteClick =  {}
        )
    }
}