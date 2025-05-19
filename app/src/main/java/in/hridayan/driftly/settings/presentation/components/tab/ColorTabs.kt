package `in`.hridayan.driftly.settings.presentation.components.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import `in`.hridayan.driftly.core.common.constants.SeedColors
import `in`.hridayan.driftly.settings.presentation.components.button.PaletteWheel
import `in`.hridayan.driftly.settings.presentation.page.lookandfeel.viewmodel.LookAndFeelViewModel

@Composable
fun ColorTabs(
    modifier: Modifier = Modifier,
    lookAndFeelViewModel: LookAndFeelViewModel = hiltViewModel()
) {
    val tonalPalettes = listOf<SeedColors>(
        SeedColors.Blue,
        SeedColors.Indigo,
        SeedColors.Purple,
        SeedColors.Pink,
        SeedColors.Red,
        SeedColors.Orange,
        SeedColors.Yellow,
        SeedColors.Teal,
        SeedColors.Green
    )
    val groupedPalettes = tonalPalettes.chunked(4)

    val pagerState = rememberPagerState(initialPage = 0) { groupedPalettes.size }

    Column(modifier = modifier) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                groupedPalettes[page].forEach { palette ->

                    var isChecked by rememberSaveable { mutableStateOf(false) }

                    PaletteWheel(
                        seedColor = Color(palette.seed),
                        primaryColor = palette.primary,
                        secondaryColor = palette.secondary,
                        tertiaryColor = palette.tertiary,
                        onClick = {
                            lookAndFeelViewModel.setSeedColor(seed = palette.seed)
                            isChecked = !isChecked
                        },
                        isChecked = isChecked,
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .padding(top = 12.dp)
                .align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            repeat(groupedPalettes.size) { index ->
                val isSelected = index == pagerState.currentPage
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(
                            if (isSelected) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.surfaceVariant
                        )
                )
            }
        }
    }
}
