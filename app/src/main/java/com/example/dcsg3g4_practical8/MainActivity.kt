package com.example.dcsg3g4_practical8

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dcsg3g4_practical8.ui.theme.DCSG3G4_Practical8Theme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DCSG3G4_Practical8Theme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    MainScreen()
                }
            }
        }
    }

    @Composable
    fun MainScreen() {
        val context = LocalContext.current
        val scope = rememberCoroutineScope()

        var username by remember { mutableStateOf("") }
        var noteText by remember { mutableStateOf("") }
        var notes by remember { mutableStateOf(listOf<Note>()) }

        LaunchedEffect(Unit) {
            username = loadUsername(context)
            notes = withContext(Dispatchers.IO) {
                loadNotes(context)
            }
        }

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.titleMedium
            )
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") }
            )
            Button(
                onClick = { saveUsername(context = context, name = username) }
            ) {
                Text("Save Setting")
            }
            HorizontalDivider()
            Text(
                text = "Notes",
                style = MaterialTheme.typography.titleMedium
            )

            OutlinedTextField(
                value = noteText,
                onValueChange = { noteText = it },
                label = { Text("New Note") }
            )

            Button(
                onClick = {
                    if (noteText.isNotBlank()) {
                        //Get maximum ID in the notes array, if null, return 0, after that, +1 to said value
                        val nextId = (notes.maxOfOrNull { it.id } ?: 0) + 1
                        //Append a new Note object to the notes array
                        val updated = notes + Note(nextId, noteText)
                        //Ensuring that the notes array is in sync
                        notes = updated
                        //Empties the noteText variable
                        noteText = ""
                        //Invoke the saveNotes function in Storage.kt
                        scope.launch {
                            withContext(Dispatchers.IO) {
                                saveNotes(context, updated)
                            }
                        }
                    }
                }
            ) {
                Text("Add Note")
            }

            if (notes.isEmpty()) {
                Text("No notes yet -- Add One!")
            }
            else {
                LazyColumn {
                    items (notes) { note ->
                        Text("•${note.text}")
                    }
                }
            }
        }
    }
}