package xyz.teamgravity.sharingdatabetweenscreens.shared_viewmodel

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import xyz.teamgravity.sharingdatabetweenscreens.presentation.component.CenterContainer

@Composable
fun SharedViewModelScreen() {
    val controller = rememberNavController()

    NavHost(
        navController = controller,
        startDestination = "boarding"
    ) {
        navigation(
            route = "boarding",
            startDestination = "personal_details"
        ) {
            composable(
                route = "personal_details"
            ) { entry ->
                val viewmodel = entry.sharedViewModel<SharedViewModel>(controller = controller)
                val state by viewmodel.state.collectAsStateWithLifecycle()

                PersonalDetailsScreen(
                    state = state,
                    onNavigate = {
                        viewmodel.onUpdateState()
                        controller.navigate("terms_and_conditions")
                    }
                )
            }
            composable(
                route = "terms_and_conditions"
            ) { entry ->
                val viewmodel = entry.sharedViewModel<SharedViewModel>(controller = controller)
                val state by viewmodel.state.collectAsStateWithLifecycle()

                TermsAndConditionsScreen(
                    state = state,
                    onFinish = {
                        controller.navigate("other") {
                            popUpTo("boarding") {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
        composable(
            route = "other"
        ) {
            OtherScreen()
        }
    }
}

@Composable
fun PersonalDetailsScreen(
    state: Int,
    onNavigate: () -> Unit
) {
    CenterContainer {
        Button(onClick = onNavigate) {
            Text(text = "Click me $state")
        }
    }
}

@Composable
fun TermsAndConditionsScreen(
    state: Int,
    onFinish: () -> Unit
) {
    CenterContainer {
        Button(onClick = onFinish) {
            Text(text = "State: $state")
        }
    }
}

@Composable
fun OtherScreen() {
    CenterContainer {
        Text(text = "Hustle Hard")
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    controller: NavHostController
): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this) {
        controller.getBackStackEntry(navGraphRoute)
    }
    return viewModel(viewModelStoreOwner = parentEntry)
}

class SharedViewModel : ViewModel() {

    private val _state = MutableStateFlow(0)
    val state: StateFlow<Int> = _state.asStateFlow()

    ///////////////////////////////////////////////////////////////////////////
    // API
    ///////////////////////////////////////////////////////////////////////////

    fun onUpdateState() {
        _state.update { it + 1 }
    }

    override fun onCleared() {
        super.onCleared()
        println("onCleared")
    }
}