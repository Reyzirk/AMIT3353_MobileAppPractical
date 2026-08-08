package com.example.dcsg2_practical7

import android.widget.Button
import android.widget.Space
import androidx.collection.ArrayMap
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ContactScreen(vm: ListViewModel = viewModel()) {
    //These variables are to be used for display purposes only.
    val items by vm.items.collectAsStateWithLifecycle()
    val name by vm.name.collectAsStateWithLifecycle()
    val phone by vm.phone.collectAsStateWithLifecycle()
    val isLoading by vm.isLoading.collectAsStateWithLifecycle()

    //Build the UI to receive user input
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        OutlinedTextField(
            value = name,
            //Whenever a new name value is changed, _name is updated
            onValueChange = vm::onNameChange,
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = phone,
            //Whenever a new phone value is changed, _phone is updated
            onValueChange = vm::onPhoneChange,
            label = { Text("Phone") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        Row {
            Button(
                //Take _name, _phone, combine them together into a Contact Obj.
                onClick = vm::addFromInput,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text("Add Contact")
            }
            Spacer(Modifier.width(8.dp))
            Button (
                onClick = vm::loadSampleContacts,
                enabled = !isLoading
            ) {
                Text(
                    text = if (isLoading) "Loading..." else "Load Sample Data"
                )
            }


        }
        //Display the loading bar
        if (isLoading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
        }

        //Display all names that was created.
        LazyColumn() {
            items(
                items = items,
                key = { it.id }
            ) { item ->
                // item variable contain a single value from items list.
                // Iterated through foreach.
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = item.name,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Phone: ${item.phone}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    IconButton(
                        onClick = { vm.remove(c = item) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete ${item.name}"
                        )
                    }
                }

            }
        }
    }
}