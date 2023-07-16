package xyz.teamgravity.sharingdatabetweenscreens.persistent_storage

import android.content.SharedPreferences
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.squareup.moshi.JsonAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import xyz.teamgravity.sharingdatabetweenscreens.presentation.component.CenterContainer
import javax.inject.Inject

@Composable
fun PersistentStorage() {
    val controller = rememberNavController()

    NavHost(
        navController = controller,
        startDestination = "screen1"
    ) {
        composable(
            route = "screen1"
        ) {
            val viewmodel = hiltViewModel<Screen1ViewModel>()

            Screen1(
                session = viewmodel.onGetSession(),
                onNavigate = {
                    viewmodel.onSaveSession()
                    controller.navigate("screen2")
                }
            )
        }
        composable(
            route = "screen2"
        ) {
            val viewmodel = hiltViewModel<Screen2ViewModel>()

            Screen2(session = viewmodel.onGetSession())
        }
    }
}

@Composable
fun Screen1(
    session: Session?,
    onNavigate: () -> Unit
) {
    CenterContainer {
        Text(
            text = session.toString(),
            textAlign = TextAlign.Center
        )
        Button(onClick = onNavigate) {
            Text(text = "Click me")
        }
    }
}

@Composable
fun Screen2(
    session: Session?
) {
    CenterContainer {
        Text(
            text = session.toString(),
            textAlign = TextAlign.Center
        )
    }
}

@HiltViewModel
class Screen1ViewModel @Inject constructor(
    private val session: SessionCache
) : ViewModel() {

    ///////////////////////////////////////////////////////////////////////////
    // API
    ///////////////////////////////////////////////////////////////////////////

    fun onGetSession(): Session? {
        return session.getSession()
    }

    fun onSaveSession() {
        session.saveSession(
            Session(
                user = User(
                    firstName = "Tupac",
                    lastName = "Shakur",
                    email = "tupacshakur@realgmail.com"
                ),
                token = "thug-for-life",
                expiresAt = Long.MAX_VALUE
            )
        )
    }
}

@HiltViewModel
class Screen2ViewModel @Inject constructor(
    private val session: SessionCache
) : ViewModel() {

    ///////////////////////////////////////////////////////////////////////////
    // API
    ///////////////////////////////////////////////////////////////////////////

    fun onGetSession(): Session? {
        return session.getSession()
    }
}

data class Session(
    val user: User,
    val token: String,
    val expiresAt: Long,
)

data class User(
    val firstName: String,
    val lastName: String,
    val email: String
)

class SessionCache(
    private val preferences: SharedPreferences,
    private val adapter: JsonAdapter<Session>
) {

    ///////////////////////////////////////////////////////////////////////////
    // API
    ///////////////////////////////////////////////////////////////////////////

    fun saveSession(session: Session) {
        preferences.edit()
            .putString(Key.SESSION, adapter.toJson(session))
            .apply()
    }

    fun getSession(): Session? {
        val json = preferences.getString(Key.SESSION, null) ?: return null
        return adapter.fromJson(json)
    }

    fun clearSession() {
        preferences.edit().remove(Key.SESSION).apply()
    }

    ///////////////////////////////////////////////////////////////////////////
    // MISC
    ///////////////////////////////////////////////////////////////////////////

    private object Key {
        const val SESSION = "session"
    }
}