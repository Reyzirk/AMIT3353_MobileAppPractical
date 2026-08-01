package com.example.dcsg1_practical5.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.dcsg1_practical5.data.ContactData
import com.example.dcsg1_practical5.model.Contact
import com.example.dcsg1_practical5.ui.theme.DCSG1_Practical5Theme

@Composable
fun ContactDetails (
    contactId: Int,
    navController: NavHostController
) {
    // Getting the contact object from the list based on the ID.
    val contact = ContactData.getById(contactId)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (contact == null) {
            Text("Contact Not Found.")
        }
        else {
            Text(
                text = contact.name,
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "Phone: ${contact.name}",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(Modifier.height(24.dp))
        Button(
            onClick = {
                navController.popBackStack()
            }
        ) {
            Text("Back")
        }
    }
}

@Preview (showBackground = true)
@Composable
fun ContactDetailsPreview() {
    DCSG1_Practical5Theme {
        ContactDetails(
            contactId = 2,
            navController = rememberNavController()
        )
    }
}