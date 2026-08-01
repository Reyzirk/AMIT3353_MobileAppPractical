package com.example.practical6.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.practical6.model.ShoppingItem
import com.example.practical6.ui.theme.Practical6Theme
import org.jetbrains.annotations.ApiStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    items: List<ShoppingItem>,
    onAddClick: () -> Unit,
    onTogglePurchased: (ShoppingItem) -> Unit,
    onDelete: (ShoppingItem) -> Unit
) {
    var itemPendingDelete by remember { mutableStateOf<ShoppingItem?>(null) }

    Scaffold(
        topBar = { TopAppBar(
            title = { Text("Shopping List Demo") }
        ) },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Item"
                )
            }
        }
    ) { innerPadding ->
        if (items.isEmpty()) {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
            ) {
                Text("No Items Yet. Tap + to add one.")
            }
        }
        else {
            //Lazy Column
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items (
                    items = items,
                    key = {it.id}
                ) { item ->
                    ShoppingItemRow(
                        item = item,
                        onCheckedChange = {onTogglePurchased(item)},
                        onDeleteClick = {itemPendingDelete = item}
                    )
                }
            }
        }
    }

    //Alert Dialog to trigger
    itemPendingDelete?.let { item ->
        AlertDialog(
            onDismissRequest = {itemPendingDelete = null},
            title = { Text("Delete Item?") },
            text = {Text("\"Are you sure you want to delete\" + item.name +\"?\"")},
            confirmButton = {
                TextButton(
                    onClick = {
                        onDelete(item)
                        itemPendingDelete = null
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {itemPendingDelete = null}
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Preview (showBackground = true)
@Composable

fun ListScreenPreview() {
    Practical6Theme {
        ListScreen(
            items = listOf(
                ShoppingItem (1, "Milk", "Groceries", "High", urgent = true),
                ShoppingItem (2, "Light Bulbs", "Household", "Low", urgent = false),
                ShoppingItem (3, "USB Cable", "Electronics", "Medium", urgent = false),
                ShoppingItem (4, "Bread", "Groceries", "Medium", urgent = false)
            ),
            onAddClick = {},
            onTogglePurchased = {},
            onDelete = {}
        )
    }
}