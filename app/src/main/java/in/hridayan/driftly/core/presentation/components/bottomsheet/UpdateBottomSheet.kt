@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)

package `in`.hridayan.driftly.core.presentation.components.bottomsheet

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import `in`.hridayan.driftly.BuildConfig
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.core.common.LocalWeakHaptic
import `in`.hridayan.driftly.core.utils.openUrl

@Composable
fun UpdateBottomSheet(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    latestVersion: String = "",
) {
    val weakHaptic = LocalWeakHaptic.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val context = LocalContext.current

    ModalBottomSheet(
        modifier = modifier,
        sheetState = sheetState,
        dragHandle = null,
        onDismissRequest = onDismiss
    ) {
        Text(
            modifier = Modifier.padding(20.dp),
            text = stringResource(R.string.update_available),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Text(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
            text = stringResource(R.string.latest_version) + " : $latestVersion",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = stringResource(R.string.current_version) + " : ${BuildConfig.VERSION_NAME}",
            style = MaterialTheme.typography.labelLarge,
        )

        Text(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 25.dp),
            text = stringResource(R.string.visit_repo_to_download),
            style = MaterialTheme.typography.bodySmall,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 25.dp)
        ) {
            OutlinedButton(
                onClick = {
                    onDismiss()
                    weakHaptic()
                },
                shapes = ButtonDefaults.shapes(),
                modifier = Modifier.weight(4f)
            ) {
                Text(text = stringResource(R.string.cancel))
            }

            Spacer(modifier.weight(1f))

            Button(
                onClick = {
                onDismiss()
                openUrl(
                    "https://github.com/DP-Hridayan/Driftly/releases/tag/$latestVersion",
                    context
                )
                weakHaptic()
            },
                shapes = ButtonDefaults.shapes(),
                modifier = Modifier.weight(4f)) {
                Text(text = stringResource(R.string.download))
            }
        }
    }
}