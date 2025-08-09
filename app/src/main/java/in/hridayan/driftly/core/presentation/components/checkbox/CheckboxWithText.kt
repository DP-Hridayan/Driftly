package `in`.hridayan.driftly.core.presentation.components.checkbox

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import `in`.hridayan.driftly.core.presentation.components.text.AutoResizeableText

@Composable
fun CheckboxWithText(
    modifier: Modifier = Modifier,
    checkedState: Boolean = false,
    onCheckChanged: () -> Unit,
    text: String,
    textColor: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            modifier = Modifier.size(16.dp),
            checked = checkedState,
            onCheckedChange = { onCheckChanged() },
        )
        Spacer(modifier = Modifier.width(10.dp))

        AutoResizeableText(
            modifier = Modifier.fillMaxWidth(),
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = textColor
        )
    }
}