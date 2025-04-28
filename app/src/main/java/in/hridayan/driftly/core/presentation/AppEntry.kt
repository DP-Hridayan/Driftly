package `in`.hridayan.driftly.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import `in`.hridayan.driftly.calender.presentation.viewmodel.CalendarViewModel
import `in`.hridayan.driftly.home.presentation.viewmodel.HomeViewmodel
import `in`.hridayan.driftly.navigation.Navigation

@Composable
fun AppEntry() {
    val homeViewModel: HomeViewmodel = hiltViewModel()
    val calendarViewModel: CalendarViewModel = hiltViewModel()

    LaunchedEffect (Unit) {
        homeViewModel.calculateTotalAttendance()
    }

    Navigation()
}
