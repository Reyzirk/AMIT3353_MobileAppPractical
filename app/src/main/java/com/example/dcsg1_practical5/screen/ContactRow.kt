package com.example.dcsg1_practical5.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dcsg1_practical5.model.Contact
import com.example.dcsg1_practical5.ui.theme.DCSG1_Practical5Theme


@Composable
fun ContactRow(
    contact: Contact,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = contact.name,
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = contact.phone,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        HorizontalDivider(
            Modifier,
            DividerDefaults.Thickness,
            DividerDefaults.color
        )
    }
}

@Preview (showBackground = true)
@Composable
fun ContactRowPreview() {
    DCSG1_Practical5Theme {
        ContactRow(
            contact = Contact (id = 1, name = "KarKai", phone = "0123 456 789"),
            onClick = {
                /*This will display the details screen on click*/
            }
        )
    }
}