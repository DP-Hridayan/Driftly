package `in`.hridayan.driftly.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import `in`.hridayan.driftly.calender.presentation.screens.CalendarScreen
import `in`.hridayan.driftly.home.presentation.screens.HomeScreen
import kotlinx.serialization.Serializable
import kotlin.math.exp

@Composable
fun Navigation() {
    val navController = rememberNavController()
    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(
            navController = navController, startDestination = HomeScreen
        ) {
            composable<HomeScreen>(
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { -(it * 0.10f).toInt() },
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = FastOutSlowInEasing
                        )
                    ) + fadeOut(
                        animationSpec = tween(
                            durationMillis = 50,
                            easing = LinearEasing
                        )
                    )
                }
                ,
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { -(it * 0.15f).toInt() },
                        animationSpec = tween(
                            durationMillis = 500,
                            easing = FastOutSlowInEasing
                        )
                    ) + fadeIn(
                        initialAlpha = 0.5f,
                        animationSpec = tween(
                            delayMillis = 66,
                            durationMillis = 50,
                            easing = LinearEasing
                        )
                    )
                }
            ) {
                HomeScreen()
            }

            composable<CalendarScreen> (
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { (it * 0.15f).toInt() },
                        animationSpec = tween(
                            durationMillis = 500,
                            easing = FastOutSlowInEasing
                        )
                    ) + fadeIn(
                        initialAlpha = 0.5f,
                        animationSpec = tween(
                            durationMillis = 350,
                            delayMillis = 66,
                            easing = LinearEasing
                        )
                    )
                }
                ,
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { (it * 0.10f).toInt() },
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = FastOutSlowInEasing
                        )
                    ) + fadeOut(
                        animationSpec = tween(
                            durationMillis = 50,
                            easing = LinearEasing
                        )
                    )
                }
            ){
                CalendarScreen()
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