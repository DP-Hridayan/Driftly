package `in`.hridayan.driftly.settings.presentation.components.radiobutton

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import `in`.hridayan.driftly.core.common.LocalWeakHaptic

@Composable
fun <T> ThemeRadioGroup(
    options: List<Pair<String, T>>,
    selected: T,
    onSelectedChange: (T) -> Unit,
    modifier: Modifier = Modifier
) {
    val weakHaptic = LocalWeakHaptic.current
    Column(modifier = modifier) {
        options.forEach { (label, value) ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onSelectedChange(value)
                        weakHaptic()
                    }
                    .padding(vertical = 8.dp, horizontal = 20.dp)
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.weight(1f))

                RadioButton(
                    selected = (value == selected),
                    onClick = {
                        onSelectedChange(value)
                        weakHaptic()
                    }
                )
            }
        }
    }
}