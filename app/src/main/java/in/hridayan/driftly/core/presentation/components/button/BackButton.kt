package `in`.hridayan.driftly.core.presentation.components.button

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.core.common.LocalWeakHaptic
import `in`.hridayan.driftly.core.presentation.components.tooltip.TooltipContent
import `in`.hridayan.driftly.navigation.LocalNavController

@Composable
fun BackButton(modifier: Modifier = Modifier) {
    val weakHaptic = LocalWeakHaptic.current
    val navController = LocalNavController.current

    TooltipContent(stringResource(R.string.back_button)) {
        IconButton(onClick = {
            weakHaptic()
            navController.popBackStack()
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