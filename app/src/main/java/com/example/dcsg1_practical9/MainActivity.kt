package com.example.dcsg1_practical9

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dcsg1_practical9.data.User
import com.example.dcsg1_practical9.data.UserInput
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


val supabase = createSupabaseClient(
    supabaseUrl = "https://eauogwigclthcxotdmae.supabase.co",
    supabaseKey = "sb_publishable_Ty0oJJKx2JA97yXyxXBJVg_oSRuhk3v"
) {
    install(Postgrest)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen() {
    //IMPORTANT -> Database CRUD
    var users by remember { mutableStateOf<List<User>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    //User Input for Name and Email
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    //Selected an Existing User for Updating purposes
    var selectedUser by remember { mutableStateOf<User?>(null) }

    //Show a message depending on the parameter passed
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    fun showMessage(message: String) {
        scope.launch {
            snackBarHostState.showSnackbar(message)
        }
    }

    //Read all contacts from the database
    suspend fun fetchAllUsers() {
        //1. Enable Loading
        //2. Go to database to retrieve all records
        //2.1 Keep these records in a List<User>
        //3. Return all the records into a singular variable
        isLoading = true
        try {
            users = withContext(Dispatchers.IO) {
                //Get supabase connection <---- supabase.
                //Go to the contact table in Supabase <----- .from("contact")
                //Select all records in the contact table <----- .select()
                //Convert them all into a List<User> <----- .decodeList<User>()
                supabase
                    .from("contact")
                    .select()
                    .decodeList<User>()
            }
        } catch (e: Exception) {
            //Show a message if it fails
            showMessage("Fetch all users operation failed: " + e.message)
        } finally {
            isLoading = false
        }
    }

    //Add New Users into the Database
    suspend fun addUser() {
        //If user did not enter a new name or email,
        //exit the function
        if (name.isBlank() || email.isBlank())
            return
        isLoading = true

        try {
            val newUser = withContext(Dispatchers.IO) {
                //1. Get Supabase Connection <---- supabase
                //2. Go to the contact table <---- .from("contact")
                //2.1 Select the entire contact table <---- select()
                //3. At the last row, (select all to know the last row)
                //4. Insert a new UserInput object <---- .insert(userInput)
                //5. Return the new User from the database <--- decodeSingle<User>()
                supabase.from("contact")
                    .insert(UserInput(name.trim(), email.trim())) {
                        select()
                    }
                    .decodeSingle<User>()
            }
            //Update the UI with the new user
            users = users + newUser
            name = ""
            email = ""
            showMessage("User added successfully")
        } catch (e: Exception) {
            showMessage("Failed to add user: " + e.message)
        } finally {
            isLoading = false
        }
    }

    //Update and Edit User Name and Email
    suspend fun updateUser() {
        //If there is no selected user to edit,
        //return out from the function
        val current = selectedUser ?: return

        //If the selected user does not have an existing name and email
        //return out from the function
        if (name.isBlank() && email.isBlank())
            return

        isLoading = true

        try {
            val updated = withContext(Dispatchers.IO) {
                supabase.from("contact")
                    .update({
                        //If user did not input anything in name or email column,
                        //Use back the default/previous value (No Change)
                        set("name", if (name.isNotBlank()) name.trim() else current.name)
                        set("email", if (email.isNotBlank()) email.trim() else current.email)
                    }) {
                        //Take everything from the database
                        select()
                        //Filter and retrieve the row that the user has modified
                        filter {
                            eq("id", current.id)
                        }
                    }.decodeSingle<User>()
            }
            //updated variable contains the latest information about the user
            //Update the user detailed in the UI.
            users = users.map { if (it.id == updated.id) updated else it }
            name = ""
            email = ""
            selectedUser = null
            showMessage("User Details updated successfully!")
        } catch (e: Exception) {
            showMessage("Failed to update user: " + e.message)
        } finally {
            isLoading = false
        }
    }

    //Delete user from database
    suspend fun deleteUser(userId: Int) {
        isLoading = true

        try {
            withContext(Dispatchers.IO) {
                supabase.from("contact").delete {
                    filter { eq("id", userId) }
                }
            }

            //Update the UI to remove the deleted user
            //Take users array, if the id of an element = user id,
            //remove the element.
            users = users.filterNot {
                it.id == userId
            }
            showMessage("User deleted successfully")
        } catch (e: Exception) {
            showMessage("Deletion Failed: " + e.message)
        } finally {
            isLoading = false
        }
    }

    //Everytime a new screen is launched,
    //run fetchAllUsers() function
    LaunchedEffect(Unit) {
        fetchAllUsers()
    }

    //UI
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Supabase User Application") }
            )
        },
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        scope.launch {
                            if (selectedUser == null)
                                addUser()
                            else
                                updateUser()
                        }
                    }
                ) {
                    Text(if (selectedUser == null) "Add User" else "Update User")
                }
                if (selectedUser != null) {
                    TextButton(
                        onClick = {
                            selectedUser = null
                            name = ""
                            email = ""
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}