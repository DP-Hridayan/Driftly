package `in`.hridayan.driftly.calender.domain.usecase

class GetWeekDayLabelsUseCase {

    operator fun invoke(isMondayFirstDay: Boolean): List<String> {
        return if (isMondayFirstDay) {
            listOf("M", "T", "W", "T", "F", "S", "S")
        } else {
            listOf("S", "M", "T", "W", "T", "F", "S")
        }
    }
}
