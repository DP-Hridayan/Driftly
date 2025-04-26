package `in`.hridayan.driftly.home.components.label

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import `in`.hridayan.driftly.core.presentation.ui.theme.Shape

@Composable
fun Label(
    modifier: Modifier = Modifier,
    text: String,
    labelColor: Color,
    strokeColor: Color,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .wrapContentSize()
            .clip(Shape.cardCornerLarge)
            .background(labelColor)
            .border(
                width = Shape.labelStroke,
                shape = Shape.cardCornerLarge,
                color = strokeColor,
            )
            .clickable(enabled = true, onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 5.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = strokeColor,
            style = MaterialTheme.typography.labelMedium,
        )
    }
}