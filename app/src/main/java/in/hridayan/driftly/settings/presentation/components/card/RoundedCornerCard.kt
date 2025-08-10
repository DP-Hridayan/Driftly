package `in`.hridayan.driftly.settings.presentation.components.card

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import `in`.hridayan.driftly.core.common.LocalSettings

@Composable
fun RoundedCornerCard(
    modifier: Modifier = Modifier,
    roundedShape: RoundedCornerShape,
    content: @Composable () -> Unit
) {
    val isDynamicColor = LocalSettings.current.isDynamicColor
    val containerColor =
        if (isDynamicColor) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surfaceContainer
    val contentColor =
        if (isDynamicColor) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface

    Card(
        modifier = modifier
            .padding(vertical = 1.dp, horizontal = 10.dp)
            .clip(roundedShape),
        shape = roundedShape,
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        content()
    }
}
