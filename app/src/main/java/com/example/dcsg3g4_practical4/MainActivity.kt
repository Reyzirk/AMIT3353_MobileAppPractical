package com.example.dcsg3g4_practical4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "form",
        modifier = modifier
    ) {
        // First destination: the input form.
        composable("form") {
            FormScreen(navController = navController)
        }
        // Second destination: takes a "name" argument in its route.
        composable(
            route = "result/{name}",
            arguments = listOf(
                navArgument("name")
                { type = NavType.StringType })
        ) { backStackEntry ->
            val name =
                backStackEntry.arguments?.getString("name") ?: ""
            ResultScreen(
                name = name, navController =
                    navController
            )
        }
    }
}

@Composable
fun ResultScreen(name: String, navController: NavHostController) {
    TODO("Not yet implemented")
}

@Composable
fun FormScreen(navController: NavHostController) {
    TODO("Not yet implemented")
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun ImagePreview() {
    DCSG3G4_Practical4Theme {
        Image(
            painter = painterResource(
                id =
                    R.drawable.ic_launcher_foreground
            ),
            contentDescription = "App logo",
            modifier = Modifier.size(120.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DCSG3G4_Practical4Theme {
        Greeting("Android")
    }
}