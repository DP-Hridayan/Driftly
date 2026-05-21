package `in`.hridayan.driftly.core.presentation.components.button

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.core.presentation.components.haptic.withHaptic
import `in`.hridayan.driftly.core.presentation.components.tooltip.TooltipContent
import `in`.hridayan.driftly.navigation.LocalNavController

@Composable
fun BackButton(modifier: Modifier = Modifier) {
    val navController = LocalNavController.current
    val lifecycleOwner = LocalLifecycleOwner.current

    TooltipContent(stringResource(R.string.back_button)) {
        IconButton(onClick = withHaptic(HapticFeedbackType.VirtualKey) {
            // Only navigate if the current lifecycle state is RESUMED.
            // This prevents multiple popBackStack calls during rapid clicks,
            // which can lead to a blank screen or an invalid navigation state.
            if (lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                navController.popBackStack()
            }
        }) {
            Icon(
                modifier = modifier,
                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}