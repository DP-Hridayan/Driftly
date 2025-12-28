@file:OptIn(ExperimentalMaterial3Api::class)

package `in`.hridayan.driftly.calender.presentation.components.bottomsheet

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import `in`.hridayan.driftly.calender.presentation.components.card.AttendanceCardWithTabs

@Composable
fun SubjectAttendanceDataBottomSheet(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    subjectId: Int = 0
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        modifier = modifier,
        sheetState = sheetState,
        onDismissRequest = onDismiss
    ) {
        AttendanceCardWithTabs(
            modifier = Modifier.fillMaxWidth(),
            subjectId = subjectId,
            sheetState = sheetState
        )
    }
}