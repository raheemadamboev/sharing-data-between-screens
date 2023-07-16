package xyz.teamgravity.sharingdatabetweenscreens.navigation_arguments

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import xyz.teamgravity.sharingdatabetweenscreens.presentation.component.CenterContainer

@Composable
fun NavigationArguments() {
    val controller = rememberNavController()

    NavHost(
        navController = controller,
        startDestination = "screen1"
    ) {
        composable(
            route = "screen1"
        ) {
            Screen1(
                onNavigate = { argument ->
                    controller.navigate("screen2/$argument")
                }
            )
        }
        composable(
            route = "screen2/{argument}",
            arguments = listOf(
                navArgument(name = "argument") {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            Screen2(argument = entry.arguments?.getString("argument") ?: "")
        }
    }
}

@Composable
fun Screen1(
    onNavigate: (String) -> Unit
) {
    CenterContainer {
        Button(
            onClick = {
                onNavigate("Fuck you!")
            }
        ) {
            Text(text = "Click me")
        }
    }
}

@Composable
fun Screen2(
    argument: String
) {
    CenterContainer {
        Text(text = argument)
    }
}