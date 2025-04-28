package `in`.hridayan.driftly.navigation

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController

val LocalNavController =
    staticCompositionLocalOf<NavHostController> { error("No LocalNavController provided") }