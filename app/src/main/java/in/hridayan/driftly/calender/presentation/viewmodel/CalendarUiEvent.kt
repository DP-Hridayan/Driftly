package `in`.hridayan.driftly.calender.presentation.viewmodel

sealed class CalendarUiEvent {
    data class ShowToast(val message: String) : CalendarUiEvent()
}
