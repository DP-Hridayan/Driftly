package `in`.hridayan.driftly.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import `in`.hridayan.driftly.calender.presentation.screens.CalendarScreen
import `in`.hridayan.driftly.home.presentation.screens.HomeScreen
import `in`.hridayan.driftly.settings.presentation.page.about.screens.AboutScreen
import `in`.hridayan.driftly.settings.presentation.page.autoupdate.screens.AutoUpdateScreen
import `in`.hridayan.driftly.settings.presentation.page.backup.screens.BackupAndRestoreScreen
import `in`.hridayan.driftly.settings.presentation.page.behavior.screens.BehaviorScreen
import `in`.hridayan.driftly.settings.presentation.page.changelog.screens.ChangelogScreen
import `in`.hridayan.driftly.settings.presentation.page.customisation.screens.CustomisationScreen
import `in`.hridayan.driftly.settings.presentation.page.lookandfeel.screens.DarkThemeScreen
import `in`.hridayan.driftly.settings.presentation.page.lookandfeel.screens.LookAndFeelScreen
import `in`.hridayan.driftly.settings.presentation.page.mainscreen.screen.SettingsScreen
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
                exitTransition = { slideFadeOutToLeft() },
                popEnterTransition = { slideFadeInFromLeft() },
                popExitTransition = { slideFadeOutToRight() }
            ) {
                LookAndFeelScreen()
            }

            composable<DarkThemeScreen>(
                enterTransition = { slideFadeInFromRight() },
                popExitTransition = { slideFadeOutToRight() }
            ) {
                DarkThemeScreen()
            }

            composable<BehaviorScreen>(
                enterTransition = { slideFadeInFromRight() },
                exitTransition = { slideFadeOutToLeft() },
                popEnterTransition = { slideFadeInFromLeft() },
                popExitTransition = { slideFadeOutToRight() }
            ) {
                BehaviorScreen()
            }

            composable<AboutScreen>(
                enterTransition = { slideFadeInFromRight() },
                exitTransition = { slideFadeOutToLeft() },
                popEnterTransition = { slideFadeInFromLeft() },
                popExitTransition = { slideFadeOutToRight() }
            ) {
                AboutScreen()
            }

            composable<ChangelogScreen>(
                enterTransition = { slideFadeInFromRight() },
                popExitTransition = { slideFadeOutToRight() }
            ) {
                ChangelogScreen()
            }

            composable<CustomisationScreen>(
                enterTransition = { slideFadeInFromRight() },
                popExitTransition = { slideFadeOutToRight() }
            ) {
                CustomisationScreen()
            }

            composable<AutoUpdateScreen>(
                enterTransition = { slideFadeInFromRight() },
                popExitTransition = { slideFadeOutToRight() }
            ) {
                AutoUpdateScreen()
            }

            composable<BackupAndRestoreScreen>(
                enterTransition = { slideFadeInFromRight() },
                popExitTransition = { slideFadeOutToRight() }
            ) {
                BackupAndRestoreScreen()
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
object DarkThemeScreen

@Serializable
object AboutScreen

@Serializable
object AutoUpdateScreen

@Serializable
object ChangelogScreen

@Serializable
object CustomisationScreen

@Serializable
object BehaviorScreen

@Serializable
object BackupAndRestoreScreen