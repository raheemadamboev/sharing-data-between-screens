package xyz.teamgravity.sharingdatabetweenscreens.composition_local

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import xyz.teamgravity.sharingdatabetweenscreens.presentation.component.CenterContainer

@Composable
fun CompositionLocal() {
    CompositionLocalProvider(LocalSnackbarHostState provides LocalSnackbarHostState.current) {
        MainScreen()
    }
}

@Composable
fun MainScreen() {
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = LocalSnackbarHostState.current)
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            Screen()
        }
    }
}

@Composable
fun Screen() {
    val snackbar = LocalSnackbarHostState.current
    val scope = rememberCoroutineScope()

    CenterContainer {
        Button(
            onClick = {
                scope.launch {
                    snackbar.showSnackbar("Maria, Mariaaaaa!")
                }
            }
        ) {
            Text(text = "Show snackbar")
        }
    }
}

val LocalSnackbarHostState: ProvidableCompositionLocal<SnackbarHostState> = compositionLocalOf {
    SnackbarHostState()
}