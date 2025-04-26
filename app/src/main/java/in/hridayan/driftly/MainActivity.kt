package `in`.hridayan.driftly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import `in`.hridayan.driftly.navigation.Navigation
import `in`.hridayan.driftly.core.presentation.ui.theme.DriftlyTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DriftlyTheme {
                Surface (
                    modifier = Modifier.Companion.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Navigation()
                }
            }
        }
    }
}


