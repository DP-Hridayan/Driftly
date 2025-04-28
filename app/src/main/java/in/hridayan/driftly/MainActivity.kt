package `in`.hridayan.driftly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import `in`.hridayan.driftly.calender.presentation.viewmodel.CalendarViewModel
import `in`.hridayan.driftly.core.presentation.AppEntry
import `in`.hridayan.driftly.core.presentation.ui.theme.DriftlyTheme
import `in`.hridayan.driftly.home.presentation.viewmodel.HomeViewmodel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()
        super.onCreate(savedInstanceState)

        val homeViewModel: HomeViewmodel by viewModels()
        val calendarViewModel: CalendarViewModel by viewModels()

        splash.setKeepOnScreenCondition {
            !(homeViewModel.isLoaded.value && calendarViewModel.isLoaded.value)
        }
        enableEdgeToEdge()
        setContent {
            DriftlyTheme {
                Surface(
                    modifier = Modifier.Companion.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    AppEntry()
                }
            }
        }
    }
}


