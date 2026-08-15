package com.example.dcsg3g4_practical8

import android.content.Context
import kotlinx.serialization.Serializable
import androidx.core.content.edit
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

@Serializable
data class Note(
    val id: Int,
    val text: String
)

private const val FILE_NAME = "notes.json"

fun saveUsername(context: Context, name: String) {
    val sharedPreference = context.getSharedPreferences(
        "settings",
        Context.MODE_PRIVATE
    )
    sharedPreference.edit {
        putString("username", name)
    }
}

fun loadUsername(context: Context): String {
    val sharedPreference = context.getSharedPreferences(
        "settings",
        Context.MODE_PRIVATE
    )
    return sharedPreference.getString("username", "") ?: ""
}

fun loadNotes(context: Context) : List<Note> {
    val file = File(context.filesDir, FILE_NAME)
    if (!file.exists()) {
        return emptyList()
    }
    else {
        try {
            return Json.decodeFromString(file.readText())
        } catch (e: Exception) { // if File Read Failure Exception
            return emptyList() // returns an empty list
        }
    }
}

fun saveNotes(context: Context, notes: List<Note>) {
    val json = Json.encodeToString(notes)
    // Opening a file
    val file = context.openFileOutput(
        FILE_NAME,
        Context.MODE_PRIVATE
    )
    //Write json variable into the file
    file.use{
        it.write(json.toByteArray())
    }
}