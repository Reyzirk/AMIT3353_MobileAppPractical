package com.example.dcsg1_practical5.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dcsg1_practical5.data.ContactData
import com.example.dcsg1_practical5.screen.ContactDetails
import com.example.dcsg1_practical5.screen.ContactListScreen

@Composable
fun AppNavGraph (
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "contactList"
    ) {
        //First Screen
        composable ("contactList") {
            ContactListScreen(
                contacts = ContactData.contacts,
                onContactClick = {id ->
                    navController.navigate("contactDetail/$id")
                }
            )
        }

        //Second Screen
        composable(
            route = "contactDetail/{contactId}",
            arguments = listOf(
                navArgument("contactId")
                {type = NavType.IntType}
            )
        ) { backStackEntry ->
            val contactId = backStackEntry.arguments?.getInt("contactId") ?: -1
            ContactDetails(
                contactId = contactId,
                navController = navController
            )
        }
    }
}