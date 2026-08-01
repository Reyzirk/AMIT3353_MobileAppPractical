package com.example.dcsg1_practical5.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dcsg1_practical5.model.Contact
import com.example.dcsg1_practical5.ui.theme.DCSG1_Practical5Theme


@Composable
//Contact List Screen is used to display a list of ContactRows.
fun ContactListScreen(
    contacts: List<Contact>, //A list of ContactRow
    onContactClick: (Int) -> Unit //When on click on any contact rows, redirect to Contact Details
) {
    //We will be building a search box.
    //This is the variable to contain the search keyword
    var query by remember { mutableStateOf("") }

    //Build the search logic.
    // If contact name contains keyword, then display,
    // if does not contain keyword, then display "No results"
    // if no search occur (default behaviour), load all contacts.
    val filteredContacts = remember(query, contacts) {
        if (query.isBlank()) {
            contacts
        } else {
            contacts.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
    }

    //UI For Search Box
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            value = query,
            label = { Text("Search contacts") },
            onValueChange = {query = it},
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )

        if (filteredContacts.isEmpty()) {
            Text(
                text = "No results found",
                modifier = Modifier.padding(16.dp)
            )
        }
        else {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items (
                    items = filteredContacts,
                    key = { it.id }
                ) { contact ->
                    //When any of these items are clicked on, will redirect to Contact Details
                    ContactRow(
                        contact = contact,
                        onClick = {onContactClick (contact.id)}
                    )
                }
            }
        }
    }

}

@Preview (showBackground = true)
@Composable
fun ContactListScreenPreview() {
    DCSG1_Practical5Theme {
        ContactListScreen(
            contacts = listOf(
                Contact(1, "Alice Tan", "012-345 6789"),
                Contact(2, "Brandon Lee", "013-456 7890"),
                Contact(3, "Chloe Wong", "014-567 8901"),
                Contact(4, "David Kumar", "015-678 9012"),
            ),
            onContactClick = {
                /*Redirects to Contact detail screen*/
            }
        )
    }
}