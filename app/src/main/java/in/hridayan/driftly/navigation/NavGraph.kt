package `in`.hridayan.driftly.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import `in`.hridayan.driftly.calender.presentation.screens.CalendarScreen
import `in`.hridayan.driftly.home.presentation.screens.HomeScreen
import `in`.hridayan.driftly.settings.page.about.screens.AboutScreen
import `in`.hridayan.driftly.settings.page.lookandfeel.screens.LookAndFeelScreen
import `in`.hridayan.driftly.settings.presentation.screens.SettingsScreen
import kotlinx.serialization.Serializable

@Composable
fun Navigation() {
    val navController = rememberNavController()
    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(
            navController = navController, startDestination = HomeScreen
        ) {
            composable<HomeScreen>(
                exitTransition = { slideFadeOutToLeft() },
                popEnterTransition = { slideFadeInFromLeft() }
            ) {
                HomeScreen()
            }

            composable<CalendarScreen>(
                enterTransition = { slideFadeInFromRight() },
                popExitTransition = { slideFadeOutToLeft() }
            ) {
                CalendarScreen()
            }

            composable<SettingsScreen>(
                enterTransition = { slideFadeInFromRight() },
                exitTransition = { slideFadeOutToLeft() },
                popEnterTransition = { slideFadeInFromLeft() },
                popExitTransition = { slideFadeOutToRight() }
            ) {
                SettingsScreen()
            }

            composable<LookAndFeelScreen>(
                enterTransition = { slideFadeInFromRight() },
                popExitTransition = { slideFadeOutToRight() }
            ) {
                LookAndFeelScreen()
            }

            composable<AboutScreen>(
                enterTransition = { slideFadeInFromRight() },
                popExitTransition = { slideFadeOutToRight() }
            ) {
                AboutScreen()
            }
        }
    }
}

@Serializable
object HomeScreen

@Serializable
data class CalendarScreen(
    val subjectId: Int, val subject: String
)

@Serializable
object SettingsScreen

@Serializable
object LookAndFeelScreen

@Serializable
object AboutScreen