package xyz.teamgravity.sharingdatabetweenscreens.stateful_dependency

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import xyz.teamgravity.sharingdatabetweenscreens.presentation.component.CenterContainer
import javax.inject.Inject

@Composable
fun StatefulDependencyScreen() {
    val controller = rememberNavController()

    NavHost(
        navController = controller,
        startDestination = "screen1"
    ) {
        composable(
            route = "screen1"
        ) {
            val viewmodel = hiltViewModel<Screen1ViewModel>()
            val count by viewmodel.count.collectAsStateWithLifecycle()

            Screen1(
                count = count,
                onNavigate = {
                    viewmodel.onIncrement()
                    controller.navigate("screen2")
                }
            )
        }
        composable(
            route = "screen2"
        ) {
            val viewmodel = hiltViewModel<Screen2ViewModel>()
            val count by viewmodel.count.collectAsStateWithLifecycle()

            Screen2(count = count)
        }
    }
}

@Composable
fun Screen1(
    count: Int,
    onNavigate: () -> Unit
) {
    CenterContainer {
        Button(onClick = onNavigate) {
            Text(text = "Screen1: $count")
        }
    }
}

@Composable
fun Screen2(
    count: Int
) {
    CenterContainer {
        Text(text = "Screen2: $count")
    }
}

class GlobalCounter {

    private val _count = MutableStateFlow(0)
    val count: StateFlow<Int> = _count.asStateFlow()

    ///////////////////////////////////////////////////////////////////////////
    // API
    ///////////////////////////////////////////////////////////////////////////

    fun onIncrement() {
        _count.update { it + 1 }
    }
}

@HiltViewModel
class Screen1ViewModel @Inject constructor(
    private val counter: GlobalCounter
) : ViewModel() {

    val count: StateFlow<Int> = counter.count

    ///////////////////////////////////////////////////////////////////////////
    // API
    ///////////////////////////////////////////////////////////////////////////

    fun onIncrement() {
        counter.onIncrement()
    }
}

@HiltViewModel
class Screen2ViewModel @Inject constructor(
    counter: GlobalCounter
) : ViewModel() {

    val count: StateFlow<Int> = counter.count
}