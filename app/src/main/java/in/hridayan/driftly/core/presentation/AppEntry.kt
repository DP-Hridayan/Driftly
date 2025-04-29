package `in`.hridayan.driftly.core.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import `in`.hridayan.driftly.calender.presentation.viewmodel.CalendarViewModel
import `in`.hridayan.driftly.home.presentation.viewmodel.HomeViewModel
import `in`.hridayan.driftly.navigation.Navigation

@Composable
fun AppEntry() {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val calendarViewModel: CalendarViewModel = hiltViewModel()

    Navigation()
}
