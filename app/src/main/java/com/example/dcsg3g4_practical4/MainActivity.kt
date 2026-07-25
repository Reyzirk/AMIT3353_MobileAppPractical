package com.example.dcsg3g4_practical4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dcsg3g4_practical4.ui.theme.DCSG3G4_Practical4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DCSG3G4_Practical4Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    var navController = rememberNavController()
                    AppNavigation(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )

                }
            }
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController, startDestination = "form", modifier = modifier
    ) {
        // First destination: the input form.
        composable("form") {
            FormScreen(navController = navController)
        }
        // Second destination: takes a "name" argument in its route.
        composable(
            route = "result/{name}", arguments = listOf(
                navArgument("name") { type = NavType.StringType })
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            ResultScreen(
                name = name, navController = navController
            )
        }
    }
}

@Composable
fun ResultScreen(name: String, navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "App Logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(140.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer)
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = "Hello! $name",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(Modifier.height(32.dp))

        Button(
            onClick = {
                /*Go back to previous screen*/
                navController.popBackStack()
            }
        ) {
            Text("Back")
        }

    }
}

@Composable
fun FormScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Application logo",
            modifier = Modifier.size(120.dp)
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Enter your name: ",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value =  name,
            onValueChange = {name = it},
            label = {Text("Name")},
            singleLine = true
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                /*When clicked, redirect to Result Screen*/
                if (name.isNotBlank()) {
                    navController.navigate("result/${name}")
                }
            },
            enabled = name.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Show Greeting")
        }
    }
}

@Preview (showBackground = true)
@Composable
fun FormScreenPreview() {
    var navController = rememberNavController()
    FormScreen(navController)
}

@Preview (showBackground = true)
@Composable
fun ResultScreenPreview() {
    var navController = rememberNavController()
    ResultScreen(
        name = "KarKai",
        navController = navController
    )
}


@Preview(showBackground = true)
@Composable
fun ImagePreview() {
    DCSG3G4_Practical4Theme {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "App logo",
            modifier = Modifier.size(120.dp)
        )
    }
}
