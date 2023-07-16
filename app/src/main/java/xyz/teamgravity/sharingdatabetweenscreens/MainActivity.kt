package xyz.teamgravity.sharingdatabetweenscreens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import xyz.teamgravity.sharingdatabetweenscreens.persistent_storage.PersistentStorage
import xyz.teamgravity.sharingdatabetweenscreens.presentation.theme.SharingDataBetweenScreensTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SharingDataBetweenScreensTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PersistentStorage()
                }
            }
        }
    }
}